window.ConfigParam = {
    total: 10,
    stars: 5
}

/**
 * [ConfigDOM 渲染DOM所需数据]
 *
 * 数据类型分为两种，一种是无分组(group)属性，如一星; 一种是包含分组，如二星。
 * @type {Object}
 */
window.ConfigDOM = {

    "一星": [{
        m: "标准选号",
        intro: "从个位选择1个或多个号码，选号与开奖号码个位一致即中奖<span class='_amount'>11</span>元。",
        type: "一星",
        pos: [1, 1]
//    ,
//        dcode:"7001",
//        scode:"40001"
    }],

    "二星": [{
        m: "标准选号",
        intro: "从00~99中任选1个或多个2位数，与开奖号码后两位相同则中奖<span class='_amount'>116.00</span>元。",
        type: "两星直选",
        pos: [2, 1]
//    ,
//        dcode:"7002",
//        scode:"40002"
    },{
        m: "组选包号",
        intro: "至少选择2个号码，开奖号码后两位含在选号中则中奖(若只选两个号，开奖号后两位为对子时不中奖)。十位个位如果为对子，奖金为<span class='_amount'>116</span>元，非对子奖金<span class='_amount'>58</span>元。",
        type: "两星组选包号",
        pos: [2, 3],
        dcode:"7024",
        scode:"40069"
    },{
        m: "组选和值",
        intro: "开奖号码十位和个位的和值与您选好一致则中奖。十位个位如果为对子，奖金为<span class='_amount' >116</span>元，非对子奖金<span class='_amount' >58</span>元。",
        type: "两星组选和值",
        pos: [2, 4],
        dcode:"7024",
        scode:"40069"
        
    },{
        m: "组选胆拖",
        intro: "从0~9中任选1个胆码,多个拖码进行投注，开奖号码后两位含在选号中则中奖(若只选两个号，开奖号后两位为对子时不中奖)。十位个位如果为对子，奖金为<span class='_amount' >116</span>元，非对子奖金<span class='_amount' >58</span>元。",
        type: "两星组选胆拖",
        pos: [2, 5],
        dcode:"7024",
        scode:"40070"
    }],

    "三星直选": [{
        m: "标准选号",
        intro: "从三位各选1个或多个号码，选号与开奖号后三位按位一致即中<span class='_amount' >1160</span>元。",
        type: "三星直选",
        pos: [3, 1],
        dcode:"7016",
        scode:"40036"
    },{
        m: "和值选号",
        intro: "所选和值与开奖号码和值一致，即中奖<span class='_amount' >1160</span>元。",
        type: "三星直选和值",
        pos: [3, 2],
        dcode:"7016",
        scode:"40037"
    },{
        m: "直选胆拖",
        intro: "选1-2个胆码，多个拖码，胆码对，剩下的开奖数字全部包含在拖码中，即中奖<span class='_amount' >1160</span>元，直选胆拖不包含组三和豹子。 ",
        type: "三星直选胆拖",
        pos: [3, 3],
        dcode:"7016",
        scode:"40038"
    },{
        m: "直选跨度",
        intro: "至少从0-9中选择1个跨度，所选跨度与开奖号码跨度一致，即中奖<span class='_amount' >1160</span>元。",
        type: "三星直选跨度",
        pos: [3, 4],
        dcode:"7016",
        scode:"40039"
    }],

    "三星组选": [{
        m: "标准",
        intro: "3个数字有两个数字相同，与开奖号后三位相同即中奖，奖金<span class='_amount' >385</span>元。",
        type: "三星组三组选",
        pos: [4, 1],
        dcode:"7018",
        scode:"40046"
    },{
        m: "包号",
        intro: "至少选择2个号码，开奖号码为组三且含在包号号码中即中奖<span class='_amount' >385</span>元。",
        type: "三星组三包号",
        pos: [4, 2],
        dcode:"7018",
        scode:"40047"
    },{
        m: "和值",
        intro: "请从1－26中选择组选三和值，单注奖金<span class='_amount' >385</span>元。",
        type: "三星组三和值",
        pos: [4, 3],
        dcode:"7018",
        scode:"40048"
    },{
        m: "包号",
        intro: "至少选择3个号码，开奖号码为组六且含在包号号码中即中奖<span class='_amount' >190</span>元。",
        type: "三星组六组选",
        pos: [4, 5],
        dcode:"7019",
        scode:"40051"
    },{
        m: "和值",
        intro: "请从3－24中选择组选六和值，单注奖金<span class='_amount' >190</span>元。",
        type: "三星组六和值",
        pos: [4, 6],
        dcode:"7019",
        scode:"40052"
    },{
        m: "胆拖",
        intro: "从0~9中任选1-2个胆码,多个拖码进行投注，开奖号码为组六且含在包号号码中即中奖<span class='_amount' >190</span>元。",
        type: "三星组六胆拖",
        pos: [4, 7],
        dcode:"7019",
        scode:"40053"
    }],

    "四星": [{
        m: "标准选号",
        intro: "从四位各选1个或多个号码，选号与开奖号后四位按位一致即中<span class='_amount' >10000</span>元,与中间三位或后三位按位一致即中<span class='_amount' >88</span>元！。",
        type: "四星直选",
        pos: [5, 1],
        dcode:"7025",
        scode:"40072"
    }],

    "五星": [{
        m: "标准选号",
        intro: "从00000~99999中任选1个或多个5位数，单注奖金<span class='_amount' >116000</span>元。",
        type: "五星直选",
        pos: [6, 1],
        dcode:"7008",
        scode:"40021"
    },{
        m: "标准选号",
        intro: "从五位各选1个号码，选号与开奖号按位一致即中<span class='_amount' >20000</span>元，前三或后三位按位一致即中<span class='_amount' >200</span>元，前二或后二位按位一致即中<span class='_amount' >30<span>元。",
        type: "五星通选",
        pos: [6, 3],
        dcode:"7009",
        scode:"40024"
    }],

    "大小单双": [{
        m: "标准选号",
        intro: "选择开奖号码的个、十两位号码属性，单注奖金<span class='_amount' >4.00</span>元。",
        type: "大小单双",
        pos: [7, 1],
        dcode:"7010",
        scode:"40025"
    }],

    "任选一": [{
        m: "标准选号",
        intro: "从5位中定位选择1个或多个号码，单注奖金<span class='_amount' >11.00</span>元。",
        type: "任选一",
        pos: [8, 1],
        dcode:"7011",
        scode:"40026"
    }],

    "任选二": [{
        m: "标准选号",
        intro: "从5位中定位选择2个或更多号码，单注奖金<span class='_amount' >116.00</span>元。",
        type: "任选二",
        pos: [9, 1],
        dcode:"7012",
        scode:"40027"
    }]
};