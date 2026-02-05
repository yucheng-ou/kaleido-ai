let api = [];
const apiDocListSize = 1
api.push({
    name: 'default',
    order: '1',
    list: []
})
api[0].list.push({
    alias: 'AdminController',
    order: '1',
    link: '管理员api',
    desc: '管理员API',
    list: []
})
api[0].list[0].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin',
    desc: '更新管理员信息(用于修改用户自身信息 不需要鉴权)',
});
api[0].list[0].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/{adminId}/enable',
    desc: '解冻管理员',
});
api[0].list[0].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/{adminId}/freeze',
    desc: '冻结管理员',
});
api[0].list[0].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/{adminId}/roles',
    desc: '分配角色给管理员',
});
api[0].list[0].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/{adminId}',
    desc: '根据ID查询管理员信息',
});
api[0].list[0].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin',
    desc: '查询管理员自身信息 不需要鉴权',
});
api[0].list[0].list.push({
    order: '7',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/page',
    desc: '分页查询管理员',
});
api[0].list[0].list.push({
    order: '8',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/{adminId}/permissions',
    desc: '获取管理员的所有权限',
});
api[0].list[0].list.push({
    order: '9',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/{adminId}/directory-menus',
    desc: '获取管理员的目录和菜单树（过滤按钮）',
});
api[0].list.push({
    alias: 'AiController',
    order: '2',
    link: 'ai管理api',
    desc: 'AI管理API',
    list: []
})
api[0].list[1].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/ai/agent',
    desc: '创建Agent',
});
api[0].list[1].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/ai/agent/{agentId}',
    desc: '更新Agent',
});
api[0].list[1].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/ai/agent/{agentId}/enable',
    desc: '启用Agent',
});
api[0].list[1].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/ai/agent/{agentId}/disable',
    desc: '禁用Agent',
});
api[0].list[1].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/ai/agent/{agentId}/tools',
    desc: '添加工具到Agent',
});
api[0].list[1].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/ai/agent/{agentId}/tools/{toolCode}',
    desc: '从Agent移除工具',
});
api[0].list[1].list.push({
    order: '7',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/ai/agent/{agentId}',
    desc: '查询Agent详情',
});
api[0].list[1].list.push({
    order: '8',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/ai/agent/by-code/{code}',
    desc: '根据编码查询Agent',
});
api[0].list[1].list.push({
    order: '9',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/ai/agent',
    desc: '查询Agent列表',
});
api[0].list[1].list.push({
    order: '10',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/ai/workflow',
    desc: '创建工作流',
});
api[0].list[1].list.push({
    order: '11',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/ai/workflow/{workflowId}',
    desc: '更新工作流',
});
api[0].list[1].list.push({
    order: '12',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/ai/workflow/{workflowId}/enable',
    desc: '启用工作流',
});
api[0].list[1].list.push({
    order: '13',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/ai/workflow/{workflowId}/disable',
    desc: '禁用工作流',
});
api[0].list[1].list.push({
    order: '14',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/ai/workflow/{workflowId}',
    desc: '查询工作流详情',
});
api[0].list[1].list.push({
    order: '15',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/ai/workflow/by-code/{code}',
    desc: '根据编码查询工作流',
});
api[0].list[1].list.push({
    order: '16',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/ai/workflow',
    desc: '查询工作流列表',
});
api[0].list.push({
    alias: 'BrandController',
    order: '3',
    link: '品牌管理api（管理后台）',
    desc: '品牌管理API（管理后台）',
    list: []
})
api[0].list[2].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/wardrobe/brand',
    desc: '创建品牌',
});
api[0].list[2].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/wardrobe/brand/{brandId}',
    desc: '更新品牌信息',
});
api[0].list[2].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/wardrobe/brand/{brandId}',
    desc: '删除品牌（逻辑删除）',
});
api[0].list[2].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/wardrobe/brand',
    desc: '查询所有品牌列表',
});
api[0].list[2].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/wardrobe/brand/{brandId}',
    desc: '根据ID查询品牌详情',
});
api[0].list.push({
    alias: 'DictController',
    order: '4',
    link: '字典控制器',
    desc: '字典控制器',
    list: []
})
api[0].list[3].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/dict',
    desc: '创建字典',
});
api[0].list[3].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/dict/{typeCode}/{dictCode}',
    desc: '更新字典',
});
api[0].list[3].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/dict/{typeCode}/{dictCode}',
    desc: '删除字典',
});
api[0].list[3].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/dict/page',
    desc: '分页查询字典列表',
});
api[0].list.push({
    alias: 'FileController',
    order: '5',
    link: '文件控制器',
    desc: '文件控制器',
    list: []
})
api[0].list[4].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/public/file/upload',
    desc: '上传文件  接收前端上传的文件，保存到MinIO',
});
api[0].list.push({
    alias: 'NoticeController',
    order: '6',
    link: '通知管理api（管理后台）',
    desc: '通知管理API（管理后台）',
    list: []
})
api[0].list[5].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/notice/template',
    desc: '添加通知模板',
});
api[0].list[5].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/notice/template/{code}',
    desc: '根据模板编码获取模板详情',
});
api[0].list[5].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/notice/{id}',
    desc: '根据ID获取通知详情',
});
api[0].list[5].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/notice/target/{target}',
    desc: '根据目标地址获取通知列表',
});
api[0].list[5].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/notice/page',
    desc: '分页查询通知列表',
});
api[0].list[5].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/notice/template/page',
    desc: '分页查询通知模板列表',
});
api[0].list.push({
    alias: 'PermissionController',
    order: '7',
    link: '权限控制器',
    desc: '权限控制器',
    list: []
})
api[0].list[6].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/permission',
    desc: '创建权限',
});
api[0].list[6].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/permission/{permissionId}',
    desc: '更新权限信息',
});
api[0].list[6].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/permission/{permissionId}/code',
    desc: '更新权限编码',
});
api[0].list[6].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/permission/{permissionId}',
    desc: '删除权限',
});
api[0].list[6].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/permission/{permissionId}',
    desc: '根据ID查询权限信息',
});
api[0].list[6].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/permission/code/{code}',
    desc: '根据编码查询权限信息',
});
api[0].list[6].list.push({
    order: '7',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/permission/parent/{parentId}',
    desc: '根据父权限ID查询子权限列表',
});
api[0].list[6].list.push({
    order: '8',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/permission/tree',
    desc: '获取权限树',
});
api[0].list.push({
    alias: 'RoleController',
    order: '8',
    link: '角色控制器',
    desc: '角色控制器',
    list: []
})
api[0].list[7].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/role',
    desc: '创建角色',
});
api[0].list[7].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/role/{roleId}',
    desc: '更新角色信息',
});
api[0].list[7].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/role/{roleId}',
    desc: '删除角色',
});
api[0].list[7].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/role/{roleId}/permissions',
    desc: '分配权限给角色',
});
api[0].list[7].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/role/{roleId}',
    desc: '根据ID查询角色信息',
});
api[0].list[7].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/role/code/{code}',
    desc: '根据编码查询角色信息',
});
api[0].list[7].list.push({
    order: '7',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/role/list',
    desc: '获取角色列表',
});
api[0].list.push({
    alias: 'UserController',
    order: '9',
    link: '用户管理api（管理后台）',
    desc: '用户管理API（管理后台）',
    list: []
})
api[0].list[8].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/user/{userId}',
    desc: '根据用户ID查询用户信息',
});
api[0].list[8].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/user/by-telephone/{telephone}',
    desc: '根据手机号查询用户信息',
});
api[0].list[8].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/user/{userId}/freeze',
    desc: '冻结用户',
});
api[0].list[8].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/user/{userId}/unfreeze',
    desc: '解冻用户',
});
api[0].list[8].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/user/{userId}',
    desc: '删除用户（软删除）',
});
api[0].list[8].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://localhost:9010/kaleido-admin/admin/user/page',
    desc: '分页查询用户列表',
});
document.onkeydown = keyDownSearch;
function keyDownSearch(e) {
    const theEvent = e;
    const code = theEvent.keyCode || theEvent.which || theEvent.charCode;
    if (code === 13) {
        const search = document.getElementById('search');
        const searchValue = search.value.toLocaleLowerCase();

        let searchGroup = [];
        for (let i = 0; i < api.length; i++) {

            let apiGroup = api[i];

            let searchArr = [];
            for (let i = 0; i < apiGroup.list.length; i++) {
                let apiData = apiGroup.list[i];
                const desc = apiData.desc;
                if (desc.toLocaleLowerCase().indexOf(searchValue) > -1) {
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
                        if (methodDesc.toLocaleLowerCase().indexOf(searchValue) > -1) {
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
            if (apiGroup.name.toLocaleLowerCase().indexOf(searchValue) > -1) {
                searchGroup.push({
                    name: apiGroup.name,
                    order: apiGroup.order,
                    list: searchArr
                });
                continue;
            }
            if (searchArr.length === 0) {
                continue;
            }
            searchGroup.push({
                name: apiGroup.name,
                order: apiGroup.order,
                list: searchArr
            });
        }
        let html;
        if (searchValue === '') {
            const liClass = "";
            const display = "display: none";
            html = buildAccordion(api,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        } else {
            const liClass = "open";
            const display = "display: block";
            html = buildAccordion(searchGroup,liClass,display);
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
            let $this = $(this), $next = $this.next();
            $next.slideToggle();
            $this.parent().toggleClass('open');
            if (!e.data.multiple) {
                $el.find('.submenu').not($next).slideUp("20").parent().removeClass('open');
            }
        };
        new Accordion($('#accordion'), false);
    }
}

function buildAccordion(apiGroups, liClass, display) {
    let html = "";
    if (apiGroups.length > 0) {
        if (apiDocListSize === 1) {
            let apiData = apiGroups[0].list;
            let order = apiGroups[0].order;
            for (let j = 0; j < apiData.length; j++) {
                html += '<li class="'+liClass+'">';
                html += '<a class="dd" href="#_'+order+'_'+apiData[j].order+'_' + apiData[j].link + '">' + apiData[j].order + '.&nbsp;' + apiData[j].desc + '</a>';
                html += '<ul class="sectlevel2" style="'+display+'">';
                let doc = apiData[j].list;
                for (let m = 0; m < doc.length; m++) {
                    let spanString;
                    if (doc[m].deprecated === 'true') {
                        spanString='<span class="line-through">';
                    } else {
                        spanString='<span>';
                    }
                    html += '<li><a href="#_'+order+'_' + apiData[j].order + '_' + doc[m].order + '_' + doc[m].desc + '">' + apiData[j].order + '.' + doc[m].order + '.&nbsp;' + spanString + doc[m].desc + '<span></a> </li>';
                }
                html += '</ul>';
                html += '</li>';
            }
        } else {
            for (let i = 0; i < apiGroups.length; i++) {
                let apiGroup = apiGroups[i];
                html += '<li class="'+liClass+'">';
                html += '<a class="dd" href="#_'+apiGroup.order+'_' + apiGroup.name + '">' + apiGroup.order + '.&nbsp;' + apiGroup.name + '</a>';
                html += '<ul class="sectlevel1">';

                let apiData = apiGroup.list;
                for (let j = 0; j < apiData.length; j++) {
                    html += '<li class="'+liClass+'">';
                    html += '<a class="dd" href="#_'+apiGroup.order+'_'+ apiData[j].order + '_'+ apiData[j].link + '">' +apiGroup.order+'.'+ apiData[j].order + '.&nbsp;' + apiData[j].desc + '</a>';
                    html += '<ul class="sectlevel2" style="'+display+'">';
                    let doc = apiData[j].list;
                    for (let m = 0; m < doc.length; m++) {
                       let spanString;
                       if (doc[m].deprecated === 'true') {
                           spanString='<span class="line-through">';
                       } else {
                           spanString='<span>';
                       }
                       html += '<li><a href="#_'+apiGroup.order+'_' + apiData[j].order + '_' + doc[m].order + '_' + doc[m].desc + '">'+apiGroup.order+'.' + apiData[j].order + '.' + doc[m].order + '.&nbsp;' + spanString + doc[m].desc + '<span></a> </li>';
                   }
                    html += '</ul>';
                    html += '</li>';
                }

                html += '</ul>';
                html += '</li>';
            }
        }
    }
    return html;
}