var app = angular.module("app", ["ui.router"]);
app.config(function ($urlRouterProvider, $stateProvider) {
	  $stateProvider
	  .state('default', { url: '/', templateUrl: './views/hsep.html' })
	  .state('hsep', { url: '/hsep', templateUrl: './views/hsep.html' })
	  .state('common', { url: '/common', templateUrl: './views/common.html' })
	  .state('system', { url: '/system', templateUrl: './views/system.html' })
	  ;
	  
	  $urlRouterProvider.otherwise('/');
	});

///consts
var $consts = $consts || {};

$consts.BASE = "http://jiajiao.tunnel.qydev.com";
$consts.COURSEBASE = $consts.BASE + '/hsep-courses';
//$consts.COURSEBASE = 'http://localhost:8080';

$consts.ORGANIZATION = "HSEP";
$consts.SYSORGANIZATION = "COMMON";

app.constant("$consts", $consts);

app.controller("coursesCommonController", function ($scope, $http, $state, $consts) {
	
	//年级管理
	$scope.stages = [];
	$scope.grades = [];
	$scope.allGrades = {};

	$http.get($consts.COURSEBASE + '/grades?organization=' + $consts.SYSORGANIZATION).success(function(response){
			angular.forEach(response.result, function(item, idx){
				var found = $scope.stages.indexOf(item.stage);
				if(found == -1){
					$scope.stages.push(item.stage);
					$scope.allGrades[item.stage] = []; 
				}
				
				if(item.name){
					$scope.allGrades[item.stage].push(item.name);
				}
		    }); 
    });
	
	//课程类别的管理
	$scope.types = [];
	$http.get($consts.COURSEBASE + '/types?organization=' + $consts.SYSORGANIZATION).success(function(response){
		angular.forEach(response.result, function(item, idx){
			$scope.types.push({id:item.id, name:item.name});
	    }); 
    }); 
	
	  $scope.count = 1;
	  
	  $scope.params = {
			    stage: "",
			    grade: "",
			    type: "",
			    organization: $consts.SYSORGANIZATION,
			    limit: "0,20",
			    paging: [0,20],
		  };
	
	  $scope.$watch('params.stage', function (newValue) {
		    $scope.grades = $scope.allGrades[newValue];
		    
		    $scope.params.paging = [0,20];
		    $scope.params.limit = $scope.params.paging.join(',');
	  });
	  $scope.$watch('params.grade', function (newValue) {
		    $scope.params.paging = [0,20];
		    $scope.params.limit = $scope.params.paging.join(',');
	  });
	  $scope.$watch('params.type', function (newValue) {
		    $scope.params.paging = [0,20];
		    $scope.params.limit = $scope.params.paging.join(',');
	  });
	  
	  /*
	   * 首先获取当前机构的所有课程
	   * 并将所有课程的年级科目存入一个数组
	   * 将common的 课程与之对比看看是否使用
	   * */
	  var coursesArray = [];
	  $http.get($consts.COURSEBASE + '/courses?organization=' + $consts.ORGANIZATION).success(function(response){
		  angular.forEach(response.result, function(item, idx){
	    	  var courseName = item.stage + item.grade + item.name;	
			  coursesArray.push(courseName);
	    });
	  });
	  
	  /*根据筛选条件 获取相应课程*/
	  $scope.search = function(){
		  $http.get($consts.COURSEBASE + '/courses', { params: $scope.params }).success(function(response){
			    $scope.courses = response.result; 
			    $scope.count = Math.floor((response.count || 1) / $scope.params.paging[1]) + ((response.count || 1) % $scope.params.paging[1] == 0 ? 0 : 1);
		  }); 
	  }
	  
	  /**
	   * page up/down
	   */
	  $(".button_page_turning").click(function (event) {
	    var button = $(event.currentTarget);
	    var direction = parseInt(button.data('direction'));
	    $scope.params.paging[0] += direction;
	    if ($scope.params.paging[0] < 1) { $scope.params.paging[0] = 0; }
	    if ($scope.params.paging[0] > $scope.count - 1) { $scope.params.paging[0] = $scope.count - 1; }
	    $scope.params.limit = $scope.params.paging.join(',');
	    $scope.search();
	  });
	  
	  /*判断当前科目是否已使用*/
	  $scope.isApply = function(stage, grade, name){
		  var currentCourse = stage+grade+name;
		  var found = null;
		  angular.forEach(coursesArray, function(item, idx){
			  if(item == currentCourse){
				   found = item;
			   } 
	      });
		  if(found) return true;
		  return false;
	  }
	  /*将当前科目添加到我的课程中*/
	  $scope.apply = function($event, id){
		  var target = $($event.currentTarget);
		  var state = target.data('state');
		  if (state === 'confirm') {
			  $http.get($consts.COURSEBASE + '/courses/' + id).success(function(response){
				    if(response.code == 0){
				    	var data = response.result;
				    	data.organization = $consts.ORGANIZATION;
				    	delete data.id;
				    	delete data.createTime;
				    	$http.put($consts.COURSEBASE + '/courses', data).success(function(response2){
				    		if(response2.code == 0){
				    			var item = data.stage+data.grade+data.name;
				    			coursesArray.push(item);
				    		}
				    	});
				    }
			  });
		      target.blur();
		      target.removeClass('btn-success').addClass('btn-warning');
		    } else {
		      target.data('state', 'confirm');
		      target.button('confirm');
		      target.removeClass('btn-warning').addClass('btn-success');
		      target.unbind('blur').blur(function (e) {
		        $(e.currentTarget).removeData('state').button('reset');
		        target.removeClass('btn-success').addClass('btn-warning');
		      });
		      return false;
		    }
		  
	  }
});

app.controller("coursesHsepController", function ($scope, $http, $state, $consts) {
	//年级管理
	$consts.stages = [];
	$consts.grades = [];
	$consts.types = [];
	$consts.allGrades = {};

	$http.get($consts.COURSEBASE + '/grades?organization=' + $consts.ORGANIZATION).success(function(response){
			angular.forEach(response.result, function(item, idx){
				var found = $consts.stages.indexOf(item.stage);
				if(found == -1){
					$consts.stages.push(item.stage);
					$consts.allGrades[item.stage] = []; 
				}
				
				if(item.name){
					$consts.allGrades[item.stage].push(item.name);
				}
		    }); 
    });
	
	//课程类别的管理
	$http.get($consts.COURSEBASE + '/types?organization=' + $consts.ORGANIZATION).success(function(response){
		angular.forEach(response.result, function(item, idx){
			$consts.types.push({id:item.id, name:item.name});
	    }); 
    }); 
	
	$scope.stages = $consts.stages;
	$scope.grades = $consts.grades;
	$scope.types = $consts.types;
	$scope.allGrades = $consts.allGrades;
	
	  $scope.count = 1;
	  $scope.params = {
			    stage: "",
			    grade: "",
			    type: "",
			    organization: $consts.ORGANIZATION,
			    limit: "0,20",
			    paging: [0,20],
		  };
	  
	  $scope.$watch('params.stage', function (newValue) {
		    $scope.grades = $scope.allGrades[newValue];
		    
		    $scope.params.paging = [0,20];
		    $scope.params.limit = $scope.params.paging.join(',');
	  });
	  $scope.$watch('params.grade', function (newValue) {
		    $scope.params.paging = [0,20];
		    $scope.params.limit = $scope.params.paging.join(',');
	  });
	  $scope.$watch('params.type', function (newValue) {
		    $scope.params.paging = [0,20];
		    $scope.params.limit = $scope.params.paging.join(',');
	  });
	  
	  $scope.$on('do-create', function (event, result) {
		    $scope.search();
	  });
	  $scope.$on('do-modify', function (event, result) {
		    $scope.search();
	  });
	  
	  /*根据筛选条件 获取相应课程*/
	  $scope.search = function(){
		  $http.get($consts.COURSEBASE + '/courses', { params: $scope.params }).success(function(response){
			    $scope.courses = response.result; 
			    $scope.count = Math.floor((response.count || 1) / $scope.params.paging[1]) + ((response.count || 1) % $scope.params.paging[1] == 0 ? 0 : 1);
		  }); 
	  }
	  
	  /**
	   * page up/down
	   */
	  $(".button_page_turning").click(function (event) {
	    var button = $(event.currentTarget);
	    var direction = parseInt(button.data('direction'));
	    $scope.params.paging[0] += direction;
	    if ($scope.params.paging[0] < 1) { $scope.params.paging[0] = 0; }
	    if ($scope.params.paging[0] > $scope.count - 1) { $scope.params.paging[0] = $scope.count - 1; }
	    $scope.params.limit = $scope.params.paging.join(',');
	    $scope.search();
	  });
	  
	  /*删除课程*/
	  $scope.cancel = function($event, id){
		  var target = $($event.currentTarget);
		  var state = target.data('state');
		  if (state === 'confirm') {
			  $http['delete']($consts.COURSEBASE + '/courses/' + id).success(function(response){
				    if(response.code == 0){
				    	$scope.search();
				    }
			  }); 
		      target.blur();
		      target.removeClass('btn-danger').addClass('btn-warning');
		    } else {
		      target.data('state', 'confirm');
		      target.button('confirm');
		      target.removeClass('btn-warning').addClass('btn-danger');
		      target.unbind('blur').blur(function (e) {
		        $(e.currentTarget).removeData('state').button('reset');
		        target.removeClass('btn-danger').addClass('btn-warning');
		      });
		      return false;
		    }
	  }
});

app.controller("courseCreationController", function ($scope, $http, $consts, $timeout) {
    $scope.stages = $consts.stages;
    $scope.allGrades = $consts.allGrades;
	
    $scope.types = $consts.types;
	
	$scope.$watch('course.stage', function (newValue) {
	   $scope.grades = $scope.allGrades[newValue];
	});
	  
	$scope.course = {
		    stage: "",
		    grade: "",
		    name: "",
		    type: "",
		    price: "",
		    hours: "",
	  };
	var modal2 = $('#modal_course_create');  
	///处理创建
	modal2.find(".btn-submit").click(function (event) {
		var course = clone($scope.course, false);
		if(course.grade == ""){
			course.grade = null;
		}
		course.price = Number(course.price)*100;
		course.hours = Number(course.hours)*3600;
		course.organization = $consts.ORGANIZATION;
	    var submit = $(event.currentTarget).button('loading');
	    $http.put($consts.COURSEBASE + '/courses', course).success(function (response) {
	      submit.button('reset');
	      if(response.code == 0) {
	        modal2.modal('hide');
	        $scope.course = {stage: "", grade: "", name: "", type: "", price: "",  hours: ""};
	        $scope.$emit('do-create', response.result);
	      } else if(response.code == -11002){
	    	modal2.find(".warning-message").html("创建课程所有项均为必填项，不能为空！").removeClass('hidden');
	    	$timeout(function(){
	  	      modal2.find(".warning-message").addClass('hidden');
	  	    },2000);
	      }else if(response.code == -11003){
	        modal2.find(".warning-message").html("该课程已存在不能重复创建，可进行修改！").removeClass('hidden');
	        $timeout(function(){
	 	       modal2.find(".warning-message").addClass('hidden');
	 	     },2000);	      
	      }
	    }).error(function (data) {
	      modal2.find(".warning-message").html('创建失败请稍后再试,或者联系管理员.').removeClass('hidden');
	      submit.button('reset');
	    });
	});
});

app.controller("courseModifyController", function ($scope, $http, $consts) {
	var modal2 = $('#modal_course_modify');  
	modal2.on('show.bs.modal', function (event) { 
	    var target = $(event.relatedTarget);
	    var courseId = target.attr('data-id') || target.data('id'); 
		$http.get($consts.COURSEBASE + '/courses/' + courseId).success(function(response){
		    $scope.course = response.result;  
		    $scope.course.price = Number(response.result.price)/100;
		    $scope.course.hours = Number(response.result.hours)/3600;
		    
	    }); 
		
	});
	modal2.find(".btn-submit").click(function (event) {
		var course = clone($scope.course, false);
		if(course.grade == ""){
			course.grade = null;
		}
		course.price = Number(course.price)*100;
		course.hours = Number(course.hours)*3600;
		course.organization = $consts.ORGANIZATION;
	    $http.post($consts.COURSEBASE + '/courses/' + $scope.course.id, course).success(function (response) {
	      if(response.code == 0) {
	        modal2.modal('hide');
	        $scope.course = {stage: "", grade: "", name: "", type: "", price: "",  hours: ""};
	        $scope.$emit('do-modify', response.result);
	      } else if(response.code == -11002){
	    	modal2.find(".warning-message").html("创建课程所有项均为必填项，不能为空！").removeClass('hidden');
	      }else if(response.code == -11003){
	        modal2.find(".warning-message").html("该课程已存在不能重复创建，可进行修改！").removeClass('hidden');
	      }
	    }).error(function (data) {
	      modal2.find(".warning-message").html('创建失败请稍后再试,或者联系管理员.').removeClass('hidden');
	      submit.button('reset');
	    });
	});
	
});
