define("static/js/module/tools",function(e,t,n){"use strict";function o(e){"string"==typeof e&&(e=document.querySelector(e));var t,n,o,a,i=e.elements.length,r=null,s=[];for(o=0;i>o;o++)switch(r=e.elements[o],r.type){case"select-one":case"select-multiple":if(r.name.length)for(a=0,t=filed.options.length;t>a;a++)option=r.options[a],option.selected&&(n="",n=option.hasAttribute?option.hasAttribute("value")?option.value:option.text:option.hasAttribute.value.specified?option.value:option.text,s.push(encodeURIComponent(r.name)+"="+encodeURIComponent(n)));break;case void 0:case"file":case"submit":case"reset":case"button":break;case"radio":case"checkbox":if(!r.checked)break;default:r.name.length&&(n=r.value,s.push(encodeURIComponent(r.name)+"="+encodeURIComponent(n.trim())))}return s.join("&")}function a(e){for(var t=e[0],n=1;n<arguments.length;n++){var o=String(arguments[n]);t+=o.replace(/&/g,"&").replace(/</g,"<").replace(/</g,">"),t+=e[n]}return t}function i(e){for(var t=e.split("?")[1],n={},o=t.split("&"),a=0,i=o.length;i>a;a++){var r=o[a].split("=");n[r[0]]=r[1]}return n}n.exports={formSerialize:o,SaferHTML:a,getParams:i}});