	var fileInputUpload;
	var fileNameUpload;
	var activeButton;
	var bb;
	var bl;

        function initializationUpload()
        {
        	$("#wrapper").children().not(":first").remove();
        	
		    fileInputUpload = document.getElementById('upload');
		    fileNameUpload = document.createElement('div');
        	fileNameUpload.style.display = 'none';
        	fileNameUpload.style.background = 'url(img/icons.png)';
        	activeButton = document.createElement('div');
        	bb = document.createElement('div');
        	bl = document.createElement('div');
        	
        	
            var wrap = document.getElementById('wrapper');
            
            fileNameUpload.setAttribute('id','FileName');
            activeButton.setAttribute('id','activeBrowseButton');
            fileInputUpload.value = '';
            fileInputUpload.onchange = HandleChanges;
            fileInputUpload.onmouseover = MakeActive;
            fileInputUpload.onmouseout = UnMakeActive;
            fileInputUpload.className = 'customFile';
            bl.className = 'blocker';
            bb.className = 'fakeButton';
            activeButton.className = 'fakeButton';
            wrap.appendChild(bb);
            wrap.appendChild(bl);
            
            wrap.appendChild(activeButton);
            
            wrap.appendChild(fileNameUpload);
           
            
        };
        function HandleChanges()
        {
        	var file = fileInputUpload.value;
            reWin = /.*\\(.*)/;
            var fileTitle = file.replace(reWin, "$1"); //выдираем название файла
            reUnix = /.*\/(.*)/;
            fileTitle = fileTitle.replace(reUnix, "$1"); //выдираем название файла
            fileNameUpload.innerHTML = fileTitle;
            
            var RegExExt =/.*\.(.*)/;
            var ext = fileTitle.replace(RegExExt, "$1");//и его расширение
            
            var pos;
            if (ext){
                switch (ext.toLowerCase())
                {
                    case 'doc': pos = '0'; break;
                    case 'bmp': pos = '16'; break;                       
                    case 'jpg': pos = '32'; break;
                    case 'jpeg': pos = '32'; break;
                    case 'png': pos = '48'; break;
                    case 'gif': pos = '64'; break;
                    case 'psd': pos = '80'; break;
                    case 'mp3': pos = '96'; break;
                    case 'wav': pos = '96'; break;
                    case 'ogg': pos = '96'; break;
                    case 'avi': pos = '112'; break;
                    case 'wmv': pos = '112'; break;
                    case 'flv': pos = '112'; break;
                    case 'pdf': pos = '128'; break;
                    case 'exe': pos = '144'; break;
                    case 'txt': pos = '160'; break;
                    default: pos = '176'; break;
                };
                fileNameUpload.style.display = 'block';
                fileNameUpload.style.background = 'url(img/icons.png) no-repeat 0 -'+pos+'px';
            };
            
        };
        function MakeActive()
        {
           activeButton.style.display = 'block';
        };
        function UnMakeActive()
        {
            activeButton.style.display = 'none';
        };