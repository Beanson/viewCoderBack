//装载数据的容器
var windowsWidth = document.body.scrollWidth;
var windowHeight = document.body.scrollHeight;
var data = {
    /*空项目需用到的数据*/
    'all_tools': {
        //数据流组件
        "Table": {},
        "List": {},
        //通用组件
        "Common_Image": {},
        "Common_Text": {},
        "Common_Background": {},
        "Common_Button": {},
        //表单组件
        "TextInput": {},
        "Password": {},
        "TextArea": {},
        "CheckBox": {},
        "RadioBox": {},
        "SelectOptions": {},
        "DatePicker": {},
        "File": {},
        "Submit": {},
        //多媒体组件
        "Video": {},
        "Sound": {},
        "Carousel": {},
        //其他组件
        "DownLoad": {},
        "List_Navigation": {},
        "CusCode": {}
    },
    /*空项目和PSD项目会用到的数据*/
    'overall': {
        'width': windowsWidth, //会依据屏幕宽度而重新赋值该width，
        'height': windowHeight,
        'is_mobile': ${is_mobile}, //标识是否是mobile网页，默认是PC网页
        'scale': false,
        'bg-color': 'rgba(255,255,255,1)',
        'max_id': 1,
        'max_rate': 1,
        'public': false, //是否为public
        'industry': '', //所属行业
        'usage_amount': 0  //使用次数
    }
};
//数据简化版保存
var mapLToS = {
    'layer_rate': 'lr',
    'layer_id': 'li',
    'data_url': 'du',
    'font-head-size': 'fhs',
    'font-head-height': 'fhh',
    'font-th-size': 'fts',
    'row_height': 'rh',

    'column_resize_key': 'crk',
    'new_column_type': 'nct',
    'row_num': 'rn',

    'margin-left': 'ml',
    'margin-right': 'mr',
    'margin-top': 'mt',
    'margin-bottom': 'mb',
    'grid_height': 'gh',
    'grid_width': 'gw',
    'column_num': 'cn',

    'grid_array': 'ga',
    'page_div_width': 'pdw',
    'new_grid_type': 'ngt',

    'widget_hover': 'wh',
    'widget_click': 'wc',
    'border_click': 'boc',
    'widget_selected': 'ws',
    'image_reposition': 'ir',

    'bg-position-left': 'bpl',
    'bg-position-top': 'bpt',
    'bg-repeat': 'br',
    'bg-size': 'bs',
    'bg-color': 'bc',
    'bg-size-res': 'bsr',

    'sub_id': 'si',
    'jump_url': 'ju',
    'link_type': 'lt',

    'border-top-width': 'btw',
    'border-right-width': 'brw',
    'border-bottom-width': 'bbw',
    'border-left-width': 'blw',
    'border-color': 'brc',

    'border-top-left-radius': 'btlr',
    'border-top-right-radius': 'btrr',
    'border-bottom-left-radius': 'bblr',
    'border-bottom-right-radius': 'bbrr',

    'text_editable': 'te',
    'temp_text': 'tt',
    'font-size': 'fsi',
    'font-weight': 'fw',
    'line-height': 'lh',
    'text-align': 'ta',
    'font-family': 'ff',
    'font-style': 'fsy',
    'text-decoration': 'td',
    'font-color': 'fc',
    'text_show': 'ts',

    'padding-left': 'pf',
    'padding-top': 'pt',
    'padding-right': 'pr',
    'padding-bottom': 'pb',

    'opt_max_val_id': 'pmvi',
    'date-time': 'dt',
    'auto_play': 'ap',

    'item-padding-left': 'ipl',
    'item-padding-top': 'ipt',
    'item-padding-right': 'ipr',
    'item-padding-bottom': 'ipb',

    'normal_status': 'ns',
    'hover_status': 'hs'
};

var num = 0, retrieveAllText = true;


/**
 * 获取该网页元素数据
 */
function getSimulateData() {
    var divs = document.querySelectorAll('div, header, footer'); //所有容器类型元素
    var imgs = document.getElementsByTagName('img'); //获取所有image组件
    var spans = document.querySelectorAll('span, a, b, p, h1, h2, h3, h4, h5, h6, dt, dd, caption, th, td, address,font, em'); //获取所有装载text组件
    var buttons = document.getElementsByTagName('button');
    var textareas = document.getElementsByTagName('textarea');
    var inputs = document.getElementsByTagName('input');

    //div元素生成
    for (var i = 0; i < divs.length; i++) {
        if (checkEleVisible(divs[i])) {
            var imgUrl = css(divs[i], 'background-image');

            //检查其background-image和background-color，判断是否为图片类型或纯背景颜色类型
            if (checkStrNotNull(imgUrl)) {
                var objImg = {};
                generalProperty(divs[i], objImg, 2, "Common_Image");
                var imgSrc = (imgUrl).substring(4, imgUrl.length - 1);
                getImgProperty(divs[i], objImg, imgSrc);
                getBgProperty(divs[i], objImg);
                responsiveSetting(objImg);
                data['all_tools']['Common_Image'][objImg['layer_id']] = objImg; //装载image类型数据

            } else if (checkBackground(divs[i])) {
                var objBg = {};
                generalProperty(divs[i], objBg, 1, "Common_Background");
                getBgProperty(divs[i], objBg);
                responsiveSetting(objBg);
                data['all_tools']['Common_Background'][objBg['layer_id']] = objBg; //装载background类型数据
            }

            //检查是否包含的数据
            var text = getText(divs[i]);
            if (text != '') {
                var objText = {};
                generalProperty(divs[i], objText, 3, "Common_Text");
                getTextProperty(divs[i], objText, text);
                getBgProperty(spans[i], objText);
                getPaddingProperty(spans[i], objText);
                objText['text-editable'] = false;
                data['all_tools']['Common_Text'][objText['layer_id']] = objText; //装载text类型数据
            }
        }
    }

    //img元素生成
    for (var i = 0; i < imgs.length; i++) {
        if (checkEleVisible(imgs[i])) {
            var objImg = {};
            generalProperty(imgs[i], objImg, 2, "Common_Image");

            //添加其他的data数据也是如此：在['dataset']后面的src/original等其他data类型，分别对应data-src, data-original等属性
            //获取img的src，如果data-src和src均为空或undefined则不添加该image对象
            var src = '';
            if (checkStrNotNull(imgs[i]['dataset']['src'])) {
                //赋值给src后，从src获取的是带有domain头部的全链接地址
                imgs[i]['src'] = imgs[i]['dataset']['src'];
                src = imgs[i]['src'];

            }
            else if (checkStrNotNull(imgs[i]['dataset']['original'])) {
                //赋值给src后，从src获取的是带有domain头部的全链接地址
                imgs[i]['src'] = imgs[i]['dataset']['original'];
                src = imgs[i]['src'];

            } else if (checkStrNotNull(imgs[i]['src'])) {
                src = imgs[i]['src']; //直接可以得到带有http头部的url

            } else {
                //有时img标签会通过background-image形式进行设置图片
                var str = css(imgs[i], 'background-image');
                if (checkStrNotNull(str)) {
                    src = str.substring(4, str.length - 1);
                } else {
                    continue
                }
            }

            getImgProperty(imgs[i], objImg, src);
            getBgProperty(imgs[i], objImg);
            responsiveSetting(objImg);
            data['all_tools']['Common_Image'][objImg['layer_id']] = objImg; //装载image类型数据
        }
    }


    //span元素生成
    for (var i = 0; i < spans.length; i++) {
        if (checkEleVisible(spans[i])) {
            var text = getText(spans[i]);
            if (text != '') {
                var objText = {};
                generalProperty(spans[i], objText, 3, "Common_Text");
                getTextProperty(spans[i], objText, text);
                getBgProperty(spans[i], objText);
                getPaddingProperty(spans[i], objText);
                objText['text-editable'] = false;
                //文字的width和height不可小于21，不然边框将无法拖动
                objText['width'] = objText['width'] < 21 ? 21 : objText['width'];
                objText['height'] = objText['height'] < 21 ? 21 : objText['height'];
                data['all_tools']['Common_Text'][objText['layer_id']] = objText; //装载text类型数据
            }
        }
    }


    //button元素，分为submit和普通button两种
    for (var i = 0; i < buttons.length; i++) {
        if (checkEleVisible(buttons[i])) {
            var text = getText(buttons[i]);
            if (text != '') {
                var objBtn = {};
                if (buttons[i].getAttribute('type') == 'submit') {
                    generalProperty(buttons[i], objBtn, 3, "Submit");
                    getBgProperty(buttons[i], objBtn);
                    getTextProperty(buttons[i], objBtn, text);
                    getPaddingProperty(buttons[i], objBtn);
                    setSubmitProperty(objBtn);
                    objBtn['text_show'] = true;
                    data['all_tools']['Submit'][objBtn['layer_id']] = objBtn; //装载Submit类型数据

                } else {
                    generalProperty(buttons[i], objBtn, 3, "Common_Button");
                    getBgProperty(buttons[i], objBtn);
                    getTextProperty(buttons[i], objBtn, text);
                    getPaddingProperty(buttons[i], objBtn);
                    objBtn['text_show'] = true;
                    data['all_tools']['Common_Button'][objBtn['layer_id']] = objBtn; //装载text类型数据
                }
            }
        }
    }

    //textarea元素
    for (var i = 0; i < textareas.length; i++) {
        if (checkEleVisible(textareas[i])) {
            var objTextArea = {};
            generalProperty(textareas[i], objTextArea, 3, "TextArea");
            getBgProperty(textareas[i], objTextArea);
            getTextProperty(textareas[i], objTextArea, null);
            getPaddingProperty(textareas[i], objTextArea);
            if (checkStrNotNull(textareas[i].getAttribute('placeholder'))) {
                objTextArea['placeholder'] = textareas[i].getAttribute('placeholder');
            }
            data['all_tools']['TextArea'][objTextArea['layer_id']] = objTextArea; //装载TextArea类型数据
        }
    }

    //input元素生成，type分别为：text, password, submit, button
    for (var i = 0; i < inputs.length; i++) {
        if (checkEleVisible(inputs[i])) {
            var objInput = {};
            switch (inputs[i].getAttribute('type')) {
                case 'text': {
                    generalProperty(inputs[i], objInput, 3, "TextInput");
                    getBgProperty(inputs[i], objInput);
                    getTextProperty(inputs[i], objInput, null);
                    getPaddingProperty(inputs[i], objInput);
                    if (checkStrNotNull(inputs[i].getAttribute('placeholder'))) {
                        objInput['placeholder'] = inputs[i].getAttribute('placeholder');
                    }
                    data['all_tools']['TextInput'][objInput['layer_id']] = objInput; //装载TextInput类型数据
                    break;
                }
                case 'password': {
                    generalProperty(inputs[i], objInput, 3, "Password");
                    getBgProperty(inputs[i], objInput);
                    getTextProperty(inputs[i], objInput, null);
                    getPaddingProperty(inputs[i], objInput);
                    if (checkStrNotNull(inputs[i].getAttribute('placeholder'))) {
                        objInput['placeholder'] = inputs[i].getAttribute('placeholder');
                    }
                    data['all_tools']['Password'][objInput['layer_id']] = objInput; //装载Password类型数据
                    break;
                }
                case 'submit': {
                    var text = inputs[i].getAttribute('value');
                    if (text != '') {
                        generalProperty(inputs[i], objInput, 3, "Submit");
                        getBgProperty(inputs[i], objInput);
                        getTextProperty(inputs[i], objInput, text);
                        getPaddingProperty(inputs[i], objInput);
                        setSubmitProperty(objInput);
                        objInput['text_show'] = true;
                        data['all_tools']['Submit'][objInput['layer_id']] = objInput; //装载Submit类型数据
                    }
                    break;
                }
                case 'button': {
                    var text = inputs[i].getAttribute('value');
                    if (text != '') {
                        generalProperty(inputs[i], objInput, 3, "Common_Button");
                        getBgProperty(inputs[i], objInput);
                        getTextProperty(inputs[i], objInput, text);
                        getPaddingProperty(inputs[i], objInput);
                        objInput['text_show'] = true;
                        data['all_tools']['Common_Button'][objInput['layer_id']] = objInput; //装载Common_Button类型数据
                    }
                    break;
                }
                default: {
                    break;
                }
            }
        }
    }


    //设置overall的max_id和max_rate --------------------------------------------------------------------------
    data['overall']['max_id'] = num;
    data['overall']['max_rate'] = 3;
    miniData(data);//数据保存最小化优化

    //executeScript方式
    return JSON.stringify(data);

    //executeAsyncScript方式
    // var callback = arguments[arguments.length - 1];
    // callback(JSON.stringify(data));

    //网页直接测试输出方式
    //console.log('data', data);
}

/**
 * 针对image和background的响应式设置
 * @param ele
 */
function responsiveSetting(ele) {
    if (Math.abs(windowsWidth - ele['width']) < 10) {
        ele['responsive'] = true;
    } else {
        ele['responsive'] = false;
    }
}

function css(ele, cssProperty) {
    return window.getComputedStyle(ele, null).getPropertyValue(cssProperty)
}

function checkEleVisible(ele) {
    var rect = ele.getBoundingClientRect();
    if (css(ele, 'display') != 'none' && css(ele, 'visibility') != 'hidden' && (rect.width * rect.height) > 0) {
        return true;
    } else {
        return false;
    }
}

function checkStrNotNull(str) {
    if (str != 'none' && str != '' && str != undefined && str != null) {
        return true;
    }
}

//结合background-color和border-color，看是否需要生成background
function checkBackground(ele) {
    var bg_color = css(ele, 'background-color');
    var border = css(ele, 'border-width');
    if (bg_color != 'rgba(0, 0, 0, 0)' || border != '0px') {
        return true
    } else {
        return false;
    }
}

function generalProperty(ele, obj, rate, type) {
    obj['layer_id'] = num++;
    obj['layer_rate'] = rate;
    obj['type'] = type;
    obj['name'] = type + '_' + num;

    //获取元素的top, left, width, height, 确保left, top大于零
    //设置width和height值, width和height大于0 在checkEleVisible处已经校验过了，无需再次校验
    var rect = ele.getBoundingClientRect();
    var width = rect.width;
    obj['width'] = width > windowsWidth ? windowsWidth : width;
    obj['height'] = rect.height;

    //设置left和top值
    var left = Math.round(rect.left + window.scrollX);
    var top = Math.round(rect.top + window.scrollY);
    obj['top'] = top > 0 ? top : 0;
    //确保左偏移大于0
    if (left > 0) {
        //保证元素都在页面上，不用左右滚动条目查找组件
        if (left + obj['width'] > windowsWidth) {
            obj['left'] = windowsWidth - obj['width'];
        } else {
            obj['left'] = left;
        }
    } else {
        obj['left'] = 0;
    }

    obj['show'] = true;
    obj['opacity'] = parseFloat(css(ele, 'opacity'));
}

function getImgProperty(ele, obj, src) {
    obj['image_reposition'] = false;
    //查看图片url是否http开头，如果是则直接使用，否则添加在url路径前面添加该网站的host
//            if (src.startsWith('http')) {
//                obj['src'] = src;
//            } else {
//                //获取host数据
//                var host = location.host;
//                if (!host.startsWith('http')) {
//                    host = location.protocol + '//' + host;
//                }
//                obj['src'] = host + src;
//            }
    obj['src'] = src;
    obj['bg-position-left'] = parseInt(css(ele, 'background-position-x'));
    obj['bg-position-top'] = parseInt(css(ele, 'background-position-y'));
    obj['bg-repeat'] = css(ele, 'background-repeat');
    obj['bg-size'] = 101;
    obj['bg-size-res'] = 'cover';
}

function getBgProperty(ele, obj) {
    obj['bg-color'] = css(ele, 'background-color');
    obj['responsive'] = false; //默认设置自动响应式为false
    obj['border-color'] = css(ele, 'border-color');
    obj['border-top-width'] = parseInt(css(ele, 'border-top-width').replace('px', ''));
    obj['border-left-width'] = parseInt(css(ele, 'border-left-width').replace('px', ''));
    obj['border-right-width'] = parseInt(css(ele, 'border-right-width').replace('px', ''));
    obj['border-bottom-width'] = parseInt(css(ele, 'border-bottom-width').replace('px', ''));
    obj['border-top-left-radius'] = parseInt(css(ele, 'border-top-left-radius').replace('px', ''));
    obj['border-top-right-radius'] = parseInt(css(ele, 'border-top-right-radius').replace('px', ''));
    obj['border-bottom-left-radius'] = parseInt(css(ele, 'border-bottom-left-radius').replace('px', ''));
    obj['border-bottom-right-radius'] = parseInt(css(ele, 'border-bottom-right-radius').replace('px', ''));
}

function getTextProperty(ele, obj, text) {
    if (text != null) {
        obj['text'] = text;
        obj['text-length'] = text.length;
    }
    obj['font-size'] = parseInt(css(ele, 'font-size').replace('px', ''));
    obj['font-weight'] = css(ele, 'font-weight');
    obj['line-height'] = 150;
    obj['text-align'] = css(ele, 'text-align');
    obj['font-family'] = css(ele, 'font-family').replace(/"/g, '').toLowerCase();
    obj['font-style'] = css(ele, 'font-style');
    obj['font-color'] = css(ele, 'color');
    obj['text-decoration'] = css(ele, 'text-decoration');
}

function getPaddingProperty(ele, obj) {
    obj['padding-left'] = parseInt(css(ele, 'padding-left').replace('px', ''));
    obj['padding-top'] = parseInt(css(ele, 'padding-top').replace('px', ''));
    obj['padding-right'] = parseInt(css(ele, 'padding-right').replace('px', ''));
    obj['padding-bottom'] = parseInt(css(ele, 'padding-bottom').replace('px', ''));
}


function setSubmitProperty(objInput) {
    objInput['text_show'] = true;
    objInput['widget_reposition'] = true;
    objInput['post_url'] = '#';
    objInput['post_param'] = {
        'TextInput': {},
        'Password': {},
        'TextArea': {},
        'CheckBox': {},
        'RadioBox': {},
        'SelectOptions': {},
        'DatePicker': {},
        'File': {},
    };
}

/**
 * 获取元素的text，如果元素有子元素则返回去除子元素后的文本
 * @param ele
 */
function getText(ele) {
    var text = '';
    if (ele.children.length > 0) {
        //设置一个开关，是否获取所有文本数据，false时只获取无child element的元素文本
        if (retrieveAllText) {
            var overallText = ele.textContent;
            for (var i = 0; i < ele.children.length; i++) {
                overallText = overallText.replace(ele.children[i].textContent, '');
            }
            text = overallText.trim();
        }
    } else {
        text = ele.textContent.trim();
    }
    return replaceSpecialCharacters(text); //替换特殊字符
}

/**
 * 替换特殊字符
 */
function replaceSpecialCharacters(text) {
    text = text.replace(/</g, '&lt;');
    text = text.replace(/>/g, '&gt;');
    return text;
}

/**
 * 数据简化版
 */
function miniData(data) {
    for (var i in data['all_tools']) {
        for (var j in data['all_tools'][i]) {
            //保证渲染数据在页面高度之内，超出的删除
            if (data['all_tools'][i][j]['top'] >= windowHeight) {
                delete data['all_tools'][i][j];
                continue
            }
            //遍历每个组件样式
            for (var k in data['all_tools'][i][j]) {
                //替换数据简化版键值
                if (checkStrNotNull(mapLToS[k])) {
                    data['all_tools'][i][j][mapLToS[k]] = data['all_tools'][i][j][k];
                    delete data['all_tools'][i][j][k]; //删除原来的键值对
                }
            }
        }
    }
}


//返回渲染数据
return getSimulateData();

