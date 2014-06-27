(function(gj) {
	function TaskManager() {
		
	};
	TaskManager.prototype.init = function(){
		this.initProjectLayout();
	};
	TaskManager.prototype.initProjectLayout = function(){
			this.getProjects();
	};
	TaskManager.prototype.showProjectCreationForm = function(){
		this.showPopupContainer('uiPopupProjectCreationForm');
	};
	TaskManager.prototype.ajaxCommonRequest = function(data){

		var url = this.getRestByAction(data.action);
		gj.ajax({
  			dataType: "json",
  			url: url,
  			data: data,
  			success: function(){
  			}
		});
		this.closePopupContainer();
	};
	TaskManager.prototype.getRestByAction = function(action){
		var rest = '/rest/taskmanagement/';
		if(action == 'createProject'){
			rest +='createProject';	
		}
		return rest;
	}
	TaskManager.prototype.getProjects = function(){
		console.info('get project json');
		this.showProjects('');
	};
	TaskManager.prototype.showProjects = function (projects){

		console.info('show projects in select');
	};
	TaskManager.prototype.getInputVal = function(inputName){

			return gj('.LightBoxContent input[name='+inputName+']').val();
	}
	TaskManager.prototype.getTextVal = function(inputName){

			return gj('.LightBoxContent #'+inputName).val();
	}
	TaskManager.prototype.doCreateProject = function(){
		alert(this.getTextVal('description'));
		var data = {
			'action':'createProject',
			'id':this.getInputVal('displayName'),
			'name':this.getInputVal('displayName'),
			'description':this.getTextVal('description'),
			'membersId':this.getInputVal('displayMember'),	
			'managerId':this.getInputVal('displayManager')		
	};

		this.ajaxCommonRequest(data);
	};
	TaskManager.prototype.closePopupContainer = function(){
		gj(".LightBoxContent").html('');
		var lightboxContainer = gj(".LightBoxContainer"); 
		gj(lightboxContainer).hide();
	}
	TaskManager.prototype.validProjectCreationForm = function (){
		console.log('validate form');
	};
	TaskManager.prototype.showPopupContainer = function(contentid){
		var childDOM = gj("#"+contentid);
		var htmlContent = childDOM.html();
		var lightboxContainer = gj(".LightBoxContainer"); 
	
		gj(".LightBoxContent").html(htmlContent);
		if(gj("#exo-mask").length == 0) {
			var mask = gj("<div />", {
				id : "exo-mask",
				style : "opacity:0.8;",
				click : function(){
					gj(".LightBoxContent").html('');
					gj(lightboxContainer).hide();
					gj(this).remove();
					gj("body").css("overflow", "auto");
				} 	
			});
		}
		gj(lightboxContainer).before(mask);

		gj("a.BtnClose").click(function(){
			gj(".LightBoxContent").html('');
			gj(lightboxContainer).hide();
			gj("#exo-mask").remove();	
			gj("body").css("overflow", "visible");
		});

		var top = (gj(window).height() - lightboxContainer.outerHeight())/2 ;
		top += gj(window).scrollTop() || 0;
		
		var left = (gj(window).width() - gj(lightboxContainer).width())/4;
		lightboxContainer.css("top", top);
		lightboxContainer.css("left", left);

		gj("body").css("overflow", "hidden");
	
		lightboxContainer.show();		
	}
	window.TaskManager = new TaskManager();
	return window.TaskManager;
})(gj);