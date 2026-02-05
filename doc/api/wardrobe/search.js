let api = [];
const apiDocListSize = 1
api.push({
    name: 'default',
    order: '1',
    list: []
})
api[0].list.push({
    alias: 'BrandController',
    order: '1',
    link: '品牌api控制器（普通用户使用）  只提供读操作接口，写操作由管理员在admin模块处理',
    desc: '品牌API控制器（普通用户使用）  只提供读操作接口，写操作由管理员在Admin模块处理',
    list: []
})
api[0].list[0].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:8082/wardrobe/brand',
    desc: '查询所有品牌列表',
});
api[0].list[0].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:8082/wardrobe/brand/{brandId}',
    desc: '根据ID查询品牌详情',
});
api[0].list.push({
    alias: 'ClothingController',
    order: '2',
    link: '服装api控制器',
    desc: '服装API控制器',
    list: []
})
api[0].list[1].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:8082/wardrobe/clothing',
    desc: '创建服装（包含图片）',
});
api[0].list[1].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:8082/wardrobe/clothing/{clothingId}',
    desc: '更新服装信息（包含图片）',
});
api[0].list[1].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:8082/wardrobe/clothing/{clothingId}',
    desc: '删除服装',
});
api[0].list[1].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:8082/wardrobe/clothing/list',
    desc: '查询用户服装列表',
});
api[0].list[1].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:8082/wardrobe/clothing/{clothingId}',
    desc: '根据ID查询服装详情',
});
api[0].list[1].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://localhost:8082/wardrobe/clothing/{clothingId}/tags/{tagId}',
    desc: '为服装添加标签',
});
api[0].list[1].list.push({
    order: '7',
    deprecated: 'false',
    url: 'http://localhost:8082/wardrobe/clothing/{clothingId}/tags/{tagId}',
    desc: '从服装移除标签',
});
api[0].list.push({
    alias: 'LocationController',
    order: '3',
    link: '位置api控制器',
    desc: '位置API控制器',
    list: []
})
api[0].list[2].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:8082/wardrobe/location',
    desc: '创建位置',
});
api[0].list[2].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:8082/wardrobe/location/{locationId}',
    desc: '更新位置信息',
});
api[0].list[2].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:8082/wardrobe/location/{locationId}',
    desc: '删除位置（逻辑删除）',
});
api[0].list[2].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:8082/wardrobe/location',
    desc: '查询当前登录用户的位置列表',
});
api[0].list[2].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:8082/wardrobe/location/{locationId}',
    desc: '根据ID查询位置详情',
});
api[0].list[2].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://localhost:8082/wardrobe/location/{locationId}/tags/{tagId}',
    desc: '为位置添加标签',
});
api[0].list[2].list.push({
    order: '7',
    deprecated: 'false',
    url: 'http://localhost:8082/wardrobe/location/{locationId}/tags/{tagId}',
    desc: '从位置移除标签',
});
api[0].list.push({
    alias: 'OutfitController',
    order: '4',
    link: '穿搭api控制器',
    desc: '穿搭API控制器',
    list: []
})
api[0].list[3].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:8082/wardrobe/outfits',
    desc: '创建穿搭（包含服装和图片）',
});
api[0].list[3].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:8082/wardrobe/outfits/{outfitId}',
    desc: '更新穿搭信息（包含服装和图片）',
});
api[0].list[3].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:8082/wardrobe/outfits/{outfitId}',
    desc: '删除穿搭',
});
api[0].list[3].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:8082/wardrobe/outfits/{outfitId}/wear',
    desc: '记录穿搭穿着',
});
api[0].list[3].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:8082/wardrobe/outfits/list',
    desc: '查询用户穿搭列表',
});
api[0].list[3].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://localhost:8082/wardrobe/outfits/{outfitId}',
    desc: '根据ID查询穿搭详情',
});
api[0].list[3].list.push({
    order: '7',
    deprecated: 'false',
    url: 'http://localhost:8082/wardrobe/outfits/{outfitId}/records',
    desc: '查询穿搭穿着记录',
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