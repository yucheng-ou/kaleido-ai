<script setup lang="ts">
import { ref, onMounted, nextTick } from 'vue'
import { Upload, Send, FileText, CheckCircle, Loader2 } from 'lucide-vue-next'
import { marked } from 'marked'
import { fetchEventSource } from '@microsoft/fetch-event-source'

// --- Interfaces ---
interface ResumeUploadResponse {
  candidateId: string
  name: string
  skills: string
  experienceYears: number
  message: string
}

interface ChatMessage {
  role: 'user' | 'ai'
  content: string
}

// --- State ---
const sessionId = ref('')
const chatInput = ref('')
const chatHistory = ref<ChatMessage[]>([])
const chatLoading = ref(false)
const chatContainerRef = ref<HTMLElement | null>(null)

const resumeFile = ref<File | null>(null)
const resumeLoading = ref(false)
const resumeResult = ref<ResumeUploadResponse | null>(null)
const resumeError = ref('')

const knowledgeFile = ref<File | null>(null)
const knowledgeLoading = ref(false)
const knowledgeSuccess = ref('')
const knowledgeError = ref('')

// --- Initialization ---
onMounted(() => {
  // Generate a random session ID
  sessionId.value = crypto.randomUUID()
  // Add initial greeting
  chatHistory.value.push({
    role: 'ai',
    content: '你好！我是你的招聘助手。我可以帮你筛选候选人、安排面试，或者回答关于公司政策的问题。请问有什么可以帮你？'
  })
})

// --- Methods ---

// 1. Resume Upload
const handleResumeFileChange = (event: Event) => {
  const target = event.target as HTMLInputElement
  if (target.files && target.files.length > 0) {
    resumeFile.value = target.files[0]
    resumeResult.value = null
    resumeError.value = ''
  }
}

const uploadResume = async () => {
  if (!resumeFile.value) return

  resumeLoading.value = true
  resumeError.value = ''
  resumeResult.value = null

  const formData = new FormData()
  formData.append('file', resumeFile.value)

  try {
    const response = await fetch('/api/candidate/upload-resume', {
      method: 'POST',
      body: formData,
    })

    if (!response.ok) {
      throw new Error(`Upload failed: ${response.statusText}`)
    }

    const result = await response.json()
    if (result.code === 200 || result.success) { // Handle Result wrapper if present
      resumeResult.value = result.data || result
    } else {
      throw new Error(result.message || 'Unknown error')
    }
  } catch (err: any) {
    resumeError.value = err.message || 'Failed to upload resume'
  } finally {
    resumeLoading.value = false
  }
}

// 2. Knowledge Upload
const handleKnowledgeFileChange = (event: Event) => {
  const target = event.target as HTMLInputElement
  if (target.files && target.files.length > 0) {
    knowledgeFile.value = target.files[0]
    knowledgeSuccess.value = ''
    knowledgeError.value = ''
  }
}

const uploadKnowledge = async () => {
  if (!knowledgeFile.value) return

  knowledgeLoading.value = true
  knowledgeError.value = ''
  knowledgeSuccess.value = ''

  const formData = new FormData()
  formData.append('file', knowledgeFile.value)

  try {
    const response = await fetch('/api/knowledge/upload', {
      method: 'POST',
      body: formData,
    })

    if (!response.ok) {
      throw new Error(`Upload failed: ${response.statusText}`)
    }

    const result = await response.json()
    if (result.code === 200 || result.success) {
      knowledgeSuccess.value = typeof result.data === 'string' ? result.data : '文档上传成功'
    } else {
      throw new Error(result.message || 'Unknown error')
    }
  } catch (err: any) {
    knowledgeError.value = err.message || 'Failed to upload document'
  } finally {
    knowledgeLoading.value = false
  }
}

// 3. Chat
const sendMessage = async () => {
  const message = chatInput.value.trim()
  if (!message || chatLoading.value) return

  // Add user message
  chatHistory.value.push({ role: 'user', content: message })
  chatInput.value = ''
  
  // Add placeholder for AI response
  const aiMessageIndex = chatHistory.value.push({ role: 'ai', content: '' }) - 1
  
  chatLoading.value = true
  scrollToBottom()

  try {
    const ctrl = new AbortController()
    await fetchEventSource('/api/agent/chat', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        sessionId: sessionId.value,
        message: message,
      }),
      signal: ctrl.signal,
      async onopen(response) {
        if (response.ok) {
          chatLoading.value = false
          return // everything's good
        } else if (response.status >= 400 && response.status < 500 && response.status !== 429) {
          throw new Error(`Client error: ${response.statusText}`)
        } else {
          throw new Error(`Server error: ${response.statusText}`)
        }
      },
      onmessage(msg) {
        if (msg.data) {
          chatHistory.value[aiMessageIndex].content += msg.data
          scrollToBottom()
        }
      },
      onclose() {
        // Stop retrying
        throw new Error('Stream complete')
      },
      onerror(err) {
        if (err.message === 'Stream complete') {
          return
        }
        throw err
      }
    })
  } catch (err: any) {
    if (err.message !== 'Stream complete') {
       chatHistory.value[aiMessageIndex].content += `\n[Error: ${err.message}]`
    }
    chatLoading.value = false
  } finally {
    chatLoading.value = false
    scrollToBottom()
  }
}

const renderMarkdown = (content: string) => {
  // marked.parse returns string | Promise<string>. Since we don't use async features, we can cast it or await it if needed.
  // But here we need synchronous return for v-html.
  // By default marked is synchronous.
  return marked.parse(content) as string
}

const scrollToBottom = () => {
  nextTick(() => {
    if (chatContainerRef.value) {
      chatContainerRef.value.scrollTop = chatContainerRef.value.scrollHeight
    }
  })
}
</script>

<template>
  <div class="h-screen overflow-hidden bg-gray-50 flex flex-col font-sans text-slate-800">
    <!-- Header -->
    <header class="flex-none bg-white border-b border-gray-200 px-6 py-4 shadow-sm flex items-center justify-between">
      <div class="flex items-center gap-2">
        <div class="w-8 h-8 bg-indigo-600 rounded-lg flex items-center justify-center text-white font-bold">K</div>
        <h1 class="text-xl font-bold text-gray-800">Kaleido 招聘助手</h1>
      </div>
      <div class="text-sm text-gray-500">Session: {{ sessionId.slice(0, 8) }}...</div>
    </header>

    <!-- Main Content -->
    <main class="flex-1 p-6 flex gap-6 overflow-hidden h-full">
      
      <!-- Left Column: Uploads -->
      <div class="w-1/3 flex flex-col gap-6 overflow-y-auto pr-2">
        
        <!-- Resume Upload Card -->
        <div class="bg-white rounded-xl shadow-sm border border-gray-100 p-6">
          <div class="flex items-center gap-2 mb-4 text-indigo-700">
            <FileText class="w-5 h-5" />
            <h2 class="font-semibold text-lg">上传简历</h2>
          </div>
          
          <div class="space-y-4">
            <div class="border-2 border-dashed border-gray-300 rounded-lg p-6 text-center hover:bg-gray-50 transition-colors cursor-pointer relative">
              <input 
                type="file" 
                accept=".pdf,.doc,.docx,.txt" 
                @change="handleResumeFileChange"
                class="absolute inset-0 w-full h-full opacity-0 cursor-pointer"
              />
              <div v-if="resumeFile" class="text-sm font-medium text-gray-700">
                {{ resumeFile.name }}
              </div>
              <div v-else class="text-gray-400 text-sm">
                点击或拖拽文件到此处<br>
                (PDF, Word, TXT)
              </div>
            </div>

            <button 
              @click="uploadResume"
              :disabled="!resumeFile || resumeLoading"
              class="w-full py-2 px-4 bg-indigo-600 hover:bg-indigo-700 disabled:bg-indigo-300 text-white rounded-lg font-medium transition-colors flex items-center justify-center gap-2"
            >
              <Loader2 v-if="resumeLoading" class="w-4 h-4 animate-spin" />
              <Upload v-else class="w-4 h-4" />
              上传并解析
            </button>

            <!-- Resume Result -->
            <div v-if="resumeResult" class="mt-4 p-4 bg-green-50 rounded-lg border border-green-100">
              <div class="flex items-center gap-2 text-green-700 font-medium mb-2">
                <CheckCircle class="w-4 h-4" />
                <span>解析成功</span>
              </div>
              <div class="text-sm space-y-1 text-gray-700">
                <p><span class="text-gray-500">姓名:</span> {{ resumeResult.name }}</p>
                <p><span class="text-gray-500">经验:</span> {{ resumeResult.experienceYears }} 年</p>
                <p><span class="text-gray-500">技能:</span> {{ resumeResult.skills }}</p>
              </div>
            </div>

            <div v-if="resumeError" class="mt-4 p-3 bg-red-50 text-red-600 text-sm rounded-lg border border-red-100">
              {{ resumeError }}
            </div>
          </div>
        </div>

        <!-- Knowledge Upload Card -->
        <div class="bg-white rounded-xl shadow-sm border border-gray-100 p-6">
          <div class="flex items-center gap-2 mb-4 text-emerald-700">
            <Upload class="w-5 h-5" />
            <h2 class="font-semibold text-lg">上传公司文档</h2>
          </div>
          
          <div class="space-y-4">
            <div class="border-2 border-dashed border-gray-300 rounded-lg p-6 text-center hover:bg-gray-50 transition-colors cursor-pointer relative">
              <input 
                type="file" 
                accept=".pdf,.doc,.docx,.txt" 
                @change="handleKnowledgeFileChange"
                class="absolute inset-0 w-full h-full opacity-0 cursor-pointer"
              />
              <div v-if="knowledgeFile" class="text-sm font-medium text-gray-700">
                {{ knowledgeFile.name }}
              </div>
              <div v-else class="text-gray-400 text-sm">
                上传规章制度、员工手册等<br>
                (PDF, Word, TXT)
              </div>
            </div>

            <button 
              @click="uploadKnowledge"
              :disabled="!knowledgeFile || knowledgeLoading"
              class="w-full py-2 px-4 bg-emerald-600 hover:bg-emerald-700 disabled:bg-emerald-300 text-white rounded-lg font-medium transition-colors flex items-center justify-center gap-2"
            >
              <Loader2 v-if="knowledgeLoading" class="w-4 h-4 animate-spin" />
              <Upload v-else class="w-4 h-4" />
              上传到知识库
            </button>

            <div v-if="knowledgeSuccess" class="mt-4 p-3 bg-emerald-50 text-emerald-700 text-sm rounded-lg border border-emerald-100 flex items-center gap-2">
              <CheckCircle class="w-4 h-4" />
              {{ knowledgeSuccess }}
            </div>

            <div v-if="knowledgeError" class="mt-4 p-3 bg-red-50 text-red-600 text-sm rounded-lg border border-red-100">
              {{ knowledgeError }}
            </div>
          </div>
        </div>

      </div>

      <!-- Right Column: Chat -->
      <div class="w-2/3 bg-white rounded-xl shadow-sm border border-gray-100 flex flex-col overflow-hidden h-full">
        <div class="p-4 border-b border-gray-100 bg-gray-50 flex justify-between items-center">
          <h2 class="font-semibold text-gray-700">AI 对话</h2>
          <span class="text-xs px-2 py-1 bg-indigo-100 text-indigo-700 rounded-full">智能助手在线</span>
        </div>

        <!-- Chat History -->
        <div ref="chatContainerRef" class="flex-1 overflow-y-auto min-h-0 p-4 space-y-4 bg-gray-50/50">
          <div 
            v-for="(msg, index) in chatHistory" 
            :key="index"
            :class="['flex w-full', msg.role === 'user' ? 'justify-end' : 'justify-start']"
          >
            <div 
              :class="[
                'max-w-[80%] px-4 py-3 text-sm leading-relaxed shadow-sm',
                msg.role === 'user' 
                  ? 'bg-indigo-600 text-white rounded-2xl rounded-br-none' 
                  : 'bg-white text-gray-800 border border-gray-100 rounded-2xl rounded-bl-none'
              ]"
            >
              <div v-if="msg.role === 'ai'" class="prose prose-sm prose-indigo max-w-none break-words" v-html="renderMarkdown(msg.content)"></div>
              <div v-else class="whitespace-pre-wrap break-words">{{ msg.content }}</div>
            </div>
          </div>

          <div v-if="chatLoading" class="flex justify-start">
            <div class="bg-white border border-gray-100 rounded-2xl rounded-bl-none px-4 py-3 shadow-sm">
              <div class="flex gap-1">
                <div class="w-2 h-2 bg-gray-400 rounded-full animate-bounce" style="animation-delay: 0ms"></div>
                <div class="w-2 h-2 bg-gray-400 rounded-full animate-bounce" style="animation-delay: 150ms"></div>
                <div class="w-2 h-2 bg-gray-400 rounded-full animate-bounce" style="animation-delay: 300ms"></div>
              </div>
            </div>
          </div>
        </div>

        <!-- Input Area -->
        <div class="p-4 bg-white border-t border-gray-100">
          <div class="flex gap-2">
            <input 
              v-model="chatInput"
              @keydown.enter="sendMessage"
              placeholder="输入消息... (例如: '帮我找一个Java开发的候选人')" 
              class="flex-1 px-4 py-2 border border-gray-200 rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-transparent transition-all"
              :disabled="chatLoading"
            />
            <button 
              @click="sendMessage"
              :disabled="!chatInput.trim() || chatLoading"
              class="px-4 py-2 bg-indigo-600 hover:bg-indigo-700 disabled:bg-gray-300 text-white rounded-lg transition-colors flex items-center justify-center"
            >
              <Send class="w-5 h-5" />
            </button>
          </div>
        </div>
      </div>

    </main>
  </div>
</template>

<style scoped>
/* Custom scrollbar for chat */
::-webkit-scrollbar {
  width: 6px;
  height: 6px;
}
::-webkit-scrollbar-track {
  background: transparent;
}
::-webkit-scrollbar-thumb {
  background: #cbd5e1;
  border-radius: 3px;
}
::-webkit-scrollbar-thumb:hover {
  background: #94a3b8;
}
</style>