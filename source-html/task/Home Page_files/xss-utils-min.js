define("SHARED/xss-utils",[],function(){eXo.define.names=[];eXo.define.deps=[];return function(){return{sanitizeString:function(a){return a.replace(/&/g,"&amp;").replace(/</g,"&lt;").replace(/>/g,"&gt;").replace(/"/g,"&quot;").replace(/'/g,"&#39;").replace(/\//g,"&#x2F;")}}}()});