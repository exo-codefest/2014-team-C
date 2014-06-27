(function() {
	function TaskManager() {
		
	};
	TaskManager.prototype.init = function(){
		this.initProjectLayout();
	};
	TaskManager.prototype.initProjectLayout = function(){
			this.getProjects();
	};
	TaskManager.prototype.showProjectCreationForm = function(){
		alert('show popup form');	
	};
	TaskManager.prototype.ajaxCommonRequest = function(action){
		alert('send request to'+action);
	};
	TaskManager.prototype.getProjects = function(){
		alert('get project json');
		this.showProjects('');
	};
	TaskManager.prototype.showProjects = function (projects){
		alert('show projects in select');
	};
	TaskManager.prototype.doCreateProject = function(){
		alert('create project');
		this.ajaxCommonRequest('createProject');
	};
	TaskManager.prototype.validProjectCreationForm = function (){
		alert('validate form');
	};
	var TaskManager = new TaskManager();
	return TaskManager;
})();

