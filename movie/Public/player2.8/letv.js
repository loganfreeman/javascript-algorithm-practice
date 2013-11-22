function $Showhtml(){
	var _nextu=3;
        var CsPlayer=Player.Url+'_letv';
      // alert('本片正在抢修，请看其他，谢谢。');
	if(Player.NextWebPage){
		_nextu=0;
	}
	player = '<embed type="application/x-shockwave-flash" src="/v/ckplayer.swf" id="movie_player" name="movie_player" bgcolor="#FFFFFF" quality="high" allowScriptAccess="always" allowfullscreen="true" flashvars="a='+encodeURIComponent(CsPlayer)+'&my_url='+encodeURIComponent(window.location.href)+'&l=&t=0&h=4&q=start&f=/v/jx/video.php?url=[$pat]&e='+_nextu+'" pluginspage="http://www.macromedia.com/go/getflashplayer" width="100%" height="'+Player.Height+'">';	
	return player;
}

function playerstop(){
	window.location.href=Player.NextWebPage;
}
Player.Show();
if(Player.Second){
	$$('buffer').style.height = Player.Height-39;
	$$("buffer").style.display = "block";
	setTimeout("Player.BufferHide();",Player.Second*1000);
}