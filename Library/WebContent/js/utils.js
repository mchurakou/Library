

var Ajax = {
	errorElementId : 'aj_error',
	
request : function(type, url, param, onSuccess, onError) {
		// parse AjaxResult object and perform corresponding actions
	var succ = function(r, status, xml) {
		if (r) {
			if (r.success) {
				if (typeof (onSuccess) === 'function') {
					onSuccess(r.data);
				}
			} else {
				if (typeof (onError) === 'function') {
					onError(r.error);
				} else {
					Ajax.defaultError(Ajax.errorElementId, r.error);
				}
			}
		}
	};

	$.ajax( {
		type : type,
		url : url,
		data : param,
		success : succ		
	});
},
post : function(url, param, onSuccess, onError) {
	// if no "param" specified, then param = onSuccess
	if (typeof (param) == 'function') {
		onError = onSuccess;
		onSuccess = param;
	}
	Ajax.request("POST", url, param, onSuccess, onError);

},

get : function(url, param, onSuccess, onError) {
	// if no "param" specified, then param = onSuccess
	if (typeof (param) == 'function') {
		onError = onSuccess;
		onSuccess = param;
	}
	Ajax.request("GET", url, param, onSuccess, onError);
},

defaultError : function(elementId, errorMsg) {
	try {
		$("#" + elementId).html(errorMsg);
	} catch (e) {
	}
}

};


/* check delete*/
function checkUpdate(response,data){
	var json = eval('(' + response.responseText + ')');
	if (!json.success)
		jAlert(json.error,MSG_ALERT);
	return [true,"success",0];

}





function isValidEmail (email, strict){
	 if ( !strict )
		 email = email.replace(/^\s+|\s+$/g, '');
	 return (/^([a-z0-9_\-]+\.)*[a-z0-9_\-]+@([a-z0-9][a-z0-9\-]*[a-z0-9]\.)+[a-z]{2,4}$/i).test(email);
	}






