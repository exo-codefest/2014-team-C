define("SHARED/juzu-ajax",["SHARED/jquery"],function(d){eXo.define.names=["jQuery"];eXo.define.deps=[d];return function(b){b.fn.jz=function(){return this.closest(".jz")};b.fn.jzURL=function(a){return this.jz().children().filter(function(){return b(this).data("method-id")==a}).map(function(){return b(this).data("url")})[0]};var d=/^(.*)\(\)$/;b.fn.jzAjax=function(a,c){if("object"===typeof a)c=a,a=c.url;var e=d.exec(a);if(null!=e&&(a=this.jzURL(e[1]),null!=a))return c=b.extend({},c||{}),c.url=a,b.ajax(c)};
b.fn.jzLoad=function(a,c,b){var f=d.exec(a);null!=f&&(a=this.jzURL(f[1])||a);"function"===typeof c&&(b=c,c=null);return this.load(a,c,b)};b.fn.jzFind=function(a){return this.jz().find(a)}}(d)});