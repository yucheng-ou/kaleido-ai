let api = [];
api.push({
    alias: 'Add dependency',
    order: '1',
    link: 'add_dependency',
    desc: 'Add dependency',
    list: []
})
api.push({
    alias: 'com.xiaoo.kaleido.api.admin.user.IRpcAdminAuthService',
    order: '2',
    link: '管理后台rpc服务接口',
    desc: '管理后台RPC服务接口',
    list: []
})
api[1].list.push({
    order: '1',
    desc: '管理员注册',
});
api[1].list.push({
    order: '2',
    desc: '记录管理员登录',
});
api[1].list.push({
    order: '3',
    desc: '根据手机号查找管理员用户',
});
api.push({
    alias: 'com.xiaoo.kaleido.api.ai.IRpcAiService',
    order: '3',
    link: 'ai_rpc服务接口
&lt;p&gt;
提供ai相关的rpc服务，供管理后台调用',
    desc: 'AI RPC服务接口
&lt;p&gt;
提供AI相关的RPC服务，供管理后台调用',
    list: []
})
api[2].list.push({
    order: '1',
    desc: '创建Agent',
});
api[2].list.push({
    order: '2',
    desc: '更新Agent',
});
api[2].list.push({
    order: '3',
    desc: '启用Agent',
});
api[2].list.push({
    order: '4',
    desc: '禁用Agent',
});
api[2].list.push({
    order: '5',
    desc: '添加工具到Agent',
});
api[2].list.push({
    order: '6',
    desc: '从Agent移除工具',
});
api[2].list.push({
    order: '7',
    desc: '创建工作流',
});
api[2].list.push({
    order: '8',
    desc: '更新工作流',
});
api[2].list.push({
    order: '9',
    desc: '启用工作流',
});
api[2].list.push({
    order: '10',
    desc: '禁用工作流',
});
api[2].list.push({
    order: '11',
    desc: '根据ID查询Agent详情',
});
api[2].list.push({
    order: '12',
    desc: '根据编码查询Agent',
});
api[2].list.push({
    order: '13',
    desc: '查询Agent列表',
});
api[2].list.push({
    order: '14',
    desc: '根据ID查询工作流详情',
});
api[2].list.push({
    order: '15',
    desc: '根据编码查询工作流',
});
api[2].list.push({
    order: '16',
    desc: '查询工作流列表',
});
api[2].list.push({
    order: '17',
    desc: '执行服装推荐工作流
&lt;p&gt;
为服装推荐服务提供的专用接口，使用固定的工作流ID为2
开始异步执行工作流，返回执行记录ID',
});
api.push({
    alias: 'com.xiaoo.kaleido.api.coin.IRpcCoinService',
    order: '4',
    link: '金币rpc服务接口
&lt;p&gt;
提供金币相关的远程过程调用服务，包括账户初始化、金币操作等功能',
    desc: '金币RPC服务接口
&lt;p&gt;
提供金币相关的远程过程调用服务，包括账户初始化、金币操作等功能',
    list: []
})
api[3].list.push({
    order: '1',
    desc: '初始化用户账户
&lt;p&gt;
为用户创建初始的金币账户，并返回创建的账户ID',
});
api[3].list.push({
    order: '2',
    desc: '处理邀请奖励
&lt;p&gt;
当新用户通过邀请注册时，为邀请人发放邀请奖励',
});
api[3].list.push({
    order: '3',
    desc: '处理位置创建扣费
&lt;p&gt;
当用户创建存储位置时，扣除相应的金币费用',
});
api[3].list.push({
    order: '4',
    desc: '处理搭配创建扣费
&lt;p&gt;
当用户创建搭配时，扣除相应的金币费用',
});
api[3].list.push({
    order: '5',
    desc: '增加金币
&lt;p&gt;
为用户账户增加指定数量的金币',
});
api[3].list.push({
    order: '6',
    desc: '减少金币
&lt;p&gt;
从用户账户减少指定数量的金币',
});
api[3].list.push({
    order: '7',
    desc: '处理推荐生成扣费
&lt;p&gt;
当用户生成AI推荐时，扣除相应的金币费用',
});
api[3].list.push({
    order: '8',
    desc: '校验金币是否足够
&lt;p&gt;
根据业务类型校验用户金币余额是否足够',
});
api.push({
    alias: 'com.xiaoo.kaleido.api.notice.IRpcNoticeService',
    order: '5',
    link: '通知rpc服务接口',
    desc: '通知RPC服务接口',
    list: []
})
api[4].list.push({
    order: '1',
    desc: '生成并发送短信验证码',
});
api[4].list.push({
    order: '2',
    desc: '校验短信验证码',
});
api[4].list.push({
    order: '3',
    desc: '添加通知模板',
});
api[4].list.push({
    order: '4',
    desc: '根据模板编码获取模板详情',
});
api[4].list.push({
    order: '5',
    desc: '根据ID获取通知详情',
});
api[4].list.push({
    order: '6',
    desc: '根据目标地址获取通知列表',
});
api[4].list.push({
    order: '7',
    desc: '分页查询通知列表',
});
api[4].list.push({
    order: '8',
    desc: '分页查询通知模板列表',
});
api.push({
    alias: 'com.xiaoo.kaleido.api.tag.IRpcTagService',
    order: '6',
    link: '标签rpc服务接口',
    desc: '标签RPC服务接口',
    list: []
})
api[5].list.push({
    order: '1',
    desc: '关联标签
&lt;p&gt;
将指定的标签关联到实体，支持批量关联多个标签',
});
api[5].list.push({
    order: '2',
    desc: '取消关联标签
&lt;p&gt;
取消标签与实体的关联关系，支持批量取消关联多个标签',
});
api[5].list.push({
    order: '3',
    desc: '查询标签关联的实体列表
&lt;p&gt;
根据标签ID查询该标签关联的所有实体ID列表',
});
api.push({
    alias: 'com.xiaoo.kaleido.api.user.IRpcUserService',
    order: '7',
    link: '用户rpc服务接口',
    desc: '用户RPC服务接口',
    list: []
})
api[6].list.push({
    order: '1',
    desc: '根据用户ID获取用户信息',
});
api[6].list.push({
    order: '2',
    desc: '根据手机号获取用户信息',
});
api[6].list.push({
    order: '3',
    desc: '用户注册',
});
api[6].list.push({
    order: '4',
    desc: '记录用户登录',
});
api[6].list.push({
    order: '5',
    desc: '记录用户登出',
});
api[6].list.push({
    order: '6',
    desc: '冻结用户',
});
api[6].list.push({
    order: '7',
    desc: '解冻用户',
});
api[6].list.push({
    order: '8',
    desc: '删除用户（软删除）',
});
api[6].list.push({
    order: '9',
    desc: '分页查询用户列表',
});
api.push({
    alias: 'com.xiaoo.kaleido.api.wardrobe.IRpcBrandService',
    order: '8',
    link: '品牌rpc服务接口',
    desc: '品牌RPC服务接口',
    list: []
})
api[7].list.push({
    order: '1',
    desc: '创建品牌',
});
api[7].list.push({
    order: '2',
    desc: '更新品牌信息',
});
api[7].list.push({
    order: '3',
    desc: '删除品牌（逻辑删除）',
});
api[7].list.push({
    order: '4',
    desc: '查询所有品牌列表',
});
api[7].list.push({
    order: '5',
    desc: '根据ID查询品牌详情',
});
api.push({
    alias: 'com.xiaoo.kaleido.api.wardrobe.IRpcClothingService',
    order: '9',
    link: '服装rpc服务接口',
    desc: '服装RPC服务接口',
    list: []
})
api[8].list.push({
    order: '1',
    desc: '根据用户ID查询服装列表
&lt;p&gt;
根据用户ID查询该用户的所有服装列表',
});
api[8].list.push({
    order: '2',
    desc: '根据ID查询服装详情
&lt;p&gt;
根据服装ID查询服装详细信息',
});
api.push({
    alias: 'com.xiaoo.kaleido.api.wardrobe.IRpcOutfitService',
    order: '10',
    link: '穿搭rpc服务接口',
    desc: '穿搭RPC服务接口',
    list: []
})
api[9].list.push({
    order: '1',
    desc: '创建穿搭（包含服装和图片）
&lt;p&gt;
为用户创建新的穿搭，包含服装列表和图片信息',
});
document.onkeydown = keyDownSearch;
function keyDownSearch(e) {
    const theEvent = e;
    const code = theEvent.keyCode || theEvent.which || theEvent.charCode;
    if (code == 13) {
        const search = document.getElementById('search');
        const searchValue = search.value;
        let searchArr = [];
        for (let i = 0; i < api.length; i++) {
            let apiData = api[i];
            const desc = apiData.desc;
            if (desc.indexOf(searchValue) > -1) {
                searchArr.push({
                    order: apiData.order,
                    desc: apiData.desc,
                    link: apiData.link,
                    list: apiData.list
                });
            } else {
                let methodList = apiData.list || [];
                let methodListTemp = [];
                for (let j = 0; j < methodList.length; j++) {
                    const methodData = methodList[j];
                    const methodDesc = methodData.desc;
                    if (methodDesc.indexOf(searchValue) > -1) {
                        methodListTemp.push(methodData);
                        break;
                    }
                }
                if (methodListTemp.length > 0) {
                    const data = {
                        order: apiData.order,
                        desc: apiData.desc,
                        link: apiData.link,
                        list: methodListTemp
                    };
                    searchArr.push(data);
                }
            }
        }
        let html;
        if (searchValue == '') {
            const liClass = "";
            const display = "display: none";
            html = buildAccordion(api,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        } else {
            const liClass = "open";
            const display = "display: block";
            html = buildAccordion(searchArr,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        }
        const Accordion = function (el, multiple) {
            this.el = el || {};
            this.multiple = multiple || false;
            const links = this.el.find('.dd');
            links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown);
        };
        Accordion.prototype.dropdown = function (e) {
            const $el = e.data.el;
            $this = $(this), $next = $this.next();
            $next.slideToggle();
            $this.parent().toggleClass('open');
            if (!e.data.multiple) {
                $el.find('.submenu').not($next).slideUp("20").parent().removeClass('open');
            }
        };
        new Accordion($('#accordion'), false);
    }
}

function buildAccordion(apiData, liClass, display) {
    let html = "";
    let doc;
    if (apiData.length > 0) {
        for (let j = 0; j < apiData.length; j++) {
            html += '<li class="'+liClass+'">';
            html += '<a class="dd" href="#_' + apiData[j].link + '">' + apiData[j].order + '.&nbsp;' + apiData[j].desc + '</a>';
            html += '<ul class="sectlevel2" style="'+display+'">';
            doc = apiData[j].list;
            for (let m = 0; m < doc.length; m++) {
                html += '<li><a href="#_' + apiData[j].order + '_' + doc[m].order + '_' + doc[m].desc + '">' + apiData[j].order + '.' + doc[m].order + '.&nbsp;' + doc[m].desc + '</a> </li>';
            }
            html += '</ul>';
            html += '</li>';
        }
    }
    return html;
}