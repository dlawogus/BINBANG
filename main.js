gogogog

"use strict";

/*global detector, location */
kindFramework
.controller('UniversalMainCtrl',
  ['$scope', '$location', '$window', '$rootScope','$timeout','$state','ModalManagerService', 'SunapiClient', 
  'UniversialManagerService', 'CAMERA_STATUS', 'ApplicationLifeCycleModel', 'CAMERA_TYPE', 'WiseListService',
  function ($scope, $location, $window, $rootScope, $timeout, $state, ModalManagerService,
          SunapiClient, UniversialManagerService, CAMERA_STATUS, ApplicationLifeCycleModel, CAMERA_TYPE, WiseListService) {

    var self = this;

    var applicationLifeCycleModel = new ApplicationLifeCycleModel();

    var quickGuide = new QuickGuide();
    
    var setInitialValue = function() {
      $scope.navOpened = false;
      $scope.isLoading = false;
      $scope.navOverlay = false;
      $scope.isNavHide = false;
    };  
    //console.log('isShowGuide '+$scope.isShowGuide);

    setInitialValue();

    $scope.toggleNav = function(val) {
      $scope.navOpened = val;
    };

    $scope.openNav = function(){
      $scope.toggleNav(true);
      $rootScope.$emit("app/scripts/directives/fullCamera.js::hiddenFunctions");
      $rootScope.$emit("allpopupclose");
    };
    
    $scope.closeNav = function(){
      $scope.toggleNav(false);
    };
    
    $scope.toggleOverlay = function(val) {
      $scope.navOverlay = val;
    };
    $scope.toggleHide = function(val) {
      $scope.isNavHide = val;
    };

    $scope.onLocation = function(param) {
      var path = $location.$$path;
      if(path.indexOf(param) < 0) {
        return true;
      } else {
        return false;
      }
    };

    var w = angular.element($window);
    w.on('resize' , function(){
      if (!$scope.$$phase) {      
        $scope.$broadcast('resize::resize');
      }
    });
    
    var goState = function(param) {
      if(param === 'uni.channel' && CAMERA_TYPE === 'b2b'){
        if(WiseListService.getCheckedList().length > 0) {
          $state.go('uni.filmLive');
          // quickGuideShow("#/uni/flimLive");
        } else {
          $state.go('uni.channellist');
          // quickGuideShow("#/uni/channellist");
        }
      } else {
        $state.go(param);
      }
      $scope.navOpened = false;
    };

    $scope.goState = function(param) {
      console.info(param);
      var path = $state.current.name;
      goState(param);
      $scope.navOpened = false;
    };

    var domControls = $scope.domControls = {
      visibilityLogoutPopup: false,
      visibilityStatusPopup: false,
    };

    $scope.resetMainMenuPopup = function() {
      domControls.visibilityStatusPopup = false;
      domControls.visibilityLogoutPopup = false;
    };

    // var quickGuideShow = function(state){
    //   this.canvas.style.display = "inline";
    //   quickGuide.show(state);  
    // };
    
    $scope.goToChannel = function() {
      $scope.goState('uni.filmLive'); 
      $scope.clearHistoryStack();
      //quickGuideShow("#/uni/flimLive");
    };

    $rootScope.$saveOn('toggleNavOpened', function(event, data) {
      console.log('toggleNavOpened is ' + data);
      $scope.navOpened = data;
    }, $scope);
    
    $rootScope.$saveOn('app/scripts/controllers/livePlayback/main.js::toggleOverlay', function(event, data) {
      console.log('toggleOverlay is ' + data);
      $scope.toggleOverlay(data);
    }, $scope);

    var showLoadingBar = function(_val) {
      $scope.isLoading = _val;
      $timeout(function(){
        $scope.$digest();
      });
    };
    
    $rootScope.$saveOn('changeLoadingBar', function(event, data){
      console.log("main.js::changeLoadingBar is Loaded with data : " + data);
      showLoadingBar(data);
    }, $scope);

    $rootScope.$saveOn('scripts/controllers/common/wrapper.js::$locationChangeStart', function(event) {
      setInitialValue();
      console.log('locationChange',event);
    }, $scope);


    var onBackbutton = function() {
      $scope.toggleNav(false);
    };

    
    
    // var orientationChangeEventListener = function(event) {
    //   if( window.orientation === 90 || window.orientation === -90) {
    //     $rootScope.orientation = "landscape";
    //   } else {
    //     $rootScope.orientation = "portrait";
    //   }
    //   console.log("orientationChangeEventListener excute");
    //   $rootScope.$emit('orientationChange');
    // }; 
    $scope.goToB2BSetup = function() {
      $scope.goState('uni.setup');
      $scope.clearHistoryStack();
    };

    $scope.goToBookmarkList = function() {
      $scope.goState('uni.bookmark.list');
      $scope.clearHistoryStack();
    };

    $scope.goToEventList = function() {
      $scope.goState('uni.event');
      $scope.clearHistoryStack();
    };

    /* 
    ** remove, change to application_lifeCycle_model
    ** foregroundEventListener
    ** backgroundEventListener
    */
    $rootScope.$saveOn('uni/main.js::ApplicationLifeCycleModel.resumeListener', function(event) {
      $scope.$broadcast('resize::resize');
    }, $scope);

    // document.addEventListener("orientationchange", orientationChangeEventListener, false);
    document.addEventListener("resume", applicationLifeCycleModel.resumeListener, false);
    document.addEventListener("pause", applicationLifeCycleModel.pauseListener, false);

    
    ////////////////////////////////////////////////////////////////////////////////
    /* 퀵가이드 캔버스 */
    ////////////////////////////////////////////////////////////////////////////////
    function QuickGuide(){
        var self = this;
        this.GUIDECHECK_KEY = 'QUICKGUIDE_Check_Key';
        this.COLOR_BG = "#000";
        this.ELEMENT_NAME = "myCanvas";
        this.canvas = document.getElementById(this.ELEMENT_NAME);
        this.ctx = this.canvas.getContext("2d");
        this.drawInfo = null;
        this.guideIndex = 0;
        // this.pre_location = window.location.hash;
        this.isSawCheck = {"#/uni/flimLive":false, "#/uni/channellist":false, "#/uni/playback":false};
        this.isShowQuickGuide = true;
        
        var $canvas = $('#'+this.ELEMENT_NAME);
        $canvas.click(function(e){
          console.log('Index?  '+self.guideIndex);
          self.next();
        });
    };
    
    QuickGuide.prototype.is_show = function(){
      return this.isShowQuickGuide;  
    }

    QuickGuide.prototype.setIs_show = function(is_show){
      this.isShowQuickGuide = is_show;

    }

    QuickGuide.prototype.init = function(){
      var self = this;

      if( this.drawInfo[this.guideIndex] ){
        this.canvas.width = this.getValue(this.drawInfo[this.guideIndex].width, true);
        this.canvas.height = this.getValue(this.drawInfo[this.guideIndex].height, false);
        this.draw(this.guideIndex);
      }
    }

    QuickGuide.prototype.next = function(){
      this.guideIndex++;    
      if(this.drawInfo){
        var len = this.drawInfo.length;
        if( this.guideIndex < len ){
          this.draw(this.guideIndex);
        }else{
          this.isSawCheck[window.location.hash] = true;
          this.hide();
        } 
      }  else{
        this.hide();
      }
      
    }
    // QuickGuide.prototype.setData = function(data){
    //   this.drawInfo = data;
    // }

    QuickGuide.prototype.show = function(state){
      this.guideIndex = 0;
      this.canvas.style.display = "inline";
      this.drawInfo = state;
      // this.drawInfo = state; quickGruideInfo[state];     

      console.log('canvas show',state, this.drawInfo, window.location.hash);
      //console.log(window.location.hash+" hash@ "+ this.pre_location);
      //SRS BF : Basic Flow
      if(this.drawInfo){
        this.init();
        // alert(window.location.hash);
      //SRS EF : Error Flow
      }else{
        //alert('fail' + window.location.hash);
      }
    }

    QuickGuide.prototype.hide = function(){
      this.canvas.style.display = "none";
      // this.isSawCheck[window.location.hash] = true;

      // if( isSawCheck["#/uni/flimLive"] === true && isSawCheck["#/uni/channellist"] === true && isSawCheck["#/uni/playback"] === true){
      //   this.setIs_show(false);
      // }
    }

    QuickGuide.prototype.draw = function(guideIndex){
        var self = this;
        self.clear();
        var list = self.drawInfo[guideIndex].list;
        //console.log(list);
        self.drawBackground(guideIndex);

        var len = list.length;
        for(var i =0; i < len; i++){
            var item = list[i];
            console.log(item);
            switch(item.type){
                case "image":
                    self.drawImage(item);
                break;
                case "text":
                    self.drawText(item);
                break;
                case "element":
                    self.drawElement(item);
                break;
            }
        }
    }

    // return value with percentage or integer value
    // param : value (80%, 200(px)), isWidth : true 가로, false 세로
    QuickGuide.prototype.getValue = function(value, isWidth){
        if(typeof value === "string"){
            var ret = 0;
            var _idx = value.indexOf("%");
            if(_idx>-1){
                var _v = parseInt(value.substring(0, _idx));
                if(isWidth){
                    ret = (window.innerWidth * _v)/100;
                }else{
                    ret = (window.innerHeight * _v)/100;
                }
            }else{
                ret = parseInt(value);
            }
            return ret;
        }else{
            return parseInt(value);    
        }
    }    

    QuickGuide.prototype.getValueW = function(value){
        this.getValue(value, true);
    }
    QuickGuide.prototype.getValueH = function(value){
        this.getValue(value, false);
    }

    QuickGuide.prototype.drawBackground = function(guideIndex){
        var self = this;
        var _info = self.drawInfo[guideIndex];
        self.ctx.fillStyle = _info.background;
        self.ctx.fillRect(self.getValue(_info.left, true),self.getValue(_info.top, false),self.getValue(_info.width, true),self.getValue(_info.height, false));
    }

    QuickGuide.prototype.drawImage = function(_info){
        var self = this;
        var imageObj = new Image();
        imageObj.onload = function() {
            self.ctx.drawImage(imageObj, self.getValue(_info.left, true),self.getValue(_info.top, false),self.getValue(_info.width, true),self.getValue(_info.height, false));
        };
        imageObj.src = _info.src;///"./fingerpoint.png";
    }

    QuickGuide.prototype.drawText = function(_info){
        var self = this;
        var textlist = _info.textlist;
        var len = textlist.length;
        
        for(var i=0; i <len; ++i){
        self.ctx.font = textlist[i].font;
          self.ctx.fillStyle = textlist[i].color;
          if( textlist[i].align === 'right' ){
            self.ctx.textAlign = 'right';
          }else{
            self.ctx.textAlign = 'left';
          }
          self.ctx.fillText(textlist[i].text, self.getValue(textlist[i].left, true), self.getValue(textlist[i].top, false) );
        }
    }

    QuickGuide.prototype.drawElement = function(_info){
      var self = this;
      var MX = self.getValue(_info.borderSize, true);
      var MW = MX*2;
      var ele = $(_info.src);
      var is_ele = $(_info.src).is(_info.src);

      console.log('ele '+is_ele);
      if( is_ele ){L
        var x = ele.offset().left - ele.position().left-MX;
        var y = ele.offset().top - ele.position().top-MX;
        var w = ele.innerWidth()+MW;
        var h = ele.innerHeight()+MW;
        self.ctx.fillStyle = _info.borderColor || "#000";
        self.ctx.fillRect(x-MX,y-MX,w+MW,h+MW);
        var x = ele.offset().left;
        var y = ele.offset().top;
        var w = ele.innerWidth();
        var h = ele.innerHeight();

        self.ctx.save();
        self.ctx.beginPath(); 
        self.ctx.clearRect(x,y,w,h);
        self.ctx.clip();
        self.ctx.restore(); 
      }
    }

    QuickGuide.prototype.clear = function(){
      var self = this;
      self.ctx.clearRect(0,0,self.canvas.width, self.canvas.height);
    }

    /////////////////////////////////////////////////////////////////
    
    quickGuide.setIs_show(true);
    
    document.addEventListener("backbutton", onBackbutton, false);

    function showQuickGuide(state){
      
      console.log('showQuickGuide show', $scope.isShowGuide ,state);
      
      if( $scope.isShowGuide && quickGruideInfo[state] ){
        quickGuide.show( quickGruideInfo[state] );
        console.log('canvas testing '+state+"----------------------------------------");
      }
    }

    $scope.$on('$stateChangeStart',function (event, toState, toParams, fromState, fromParams) {
      if(toState.name === 'login') document.removeEventListener("backbutton", onBackbutton, false);
      if( localStorage.getItem(quickGuide.GUIDECHECK_KEY) === true ){
        $scope.isShowGuide = true;
      }else{
        $scope.isShowGuide = false;
      }
      var state = '#/uni/'+toState.name.split('.')[1];
      showQuickGuide(state);
      console.log('canvas show',quickGuide.is_show(),state);
    });

    setTimeout(function(){
      console.log("window.location ", window.location, window.location.hash);
      var state = window.location.hash;//'#/uni/'+toState.name.split('.')[1];
      showQuickGuide(state);
    });

    //////////////////////////////////////////////////////////////////////////////////////////////


    $scope.showGuideCheck = function() {
      //console.log('IsShow? '+$scope.isShowGuide);
      //localStorage.getItem(STORAGE_KEY);
      // localStorage.setItem(STORAGE_KEY, $scope.isShowGuide);
      //console.log(localStorage.getItem(quickGuide.GUIDECHECK_KEY) );
      localStorage.setItem(quickGuide.GUIDECHECK_KEY, $scope.isShowGuide );
      console.log('checkbox push', localStorage.getItem(quickGuide.GUIDECHECK_KEY));
      console.log('checkbox push', $scope.isShowGuide );
    };

    console.log('Main.js 실행');

    
    console.log('check',localStorage.getItem(quickGuide.GUIDECHECK_KEY) );
      if( localStorage.getItem(quickGuide.GUIDECHECK_KEY) === null ){
        localStorage.setItem(quickGuide.GUIDECHECK_KEY, true);
        $scope.isShowGuide = true;
      }else{
        if( localStorage.getItem(quickGuide.GUIDECHECK_KEY) === true ){
            $scope.isShowGuide = true;
        }else{
            $scope.isShowGuide = false;
        }
      }
      console.log('check-1',localStorage.getItem(quickGuide.GUIDECHECK_KEY) );
  }

]);

