function $Showhtml()
{
	player = '<embed type="application/x-shockwave-flash" src="/v/v.swf" id="movie_player" name="movie_player" bgcolor="#FFFFFF" quality="high" allowfullscreen="true" flashvars="f=/v/qiyi.php?url=[$pat]&a='+Player.Url+'_dd1&s=2&e=4&g='+parent.parent.vod_piantou+'&j='+parent.parent.vod_pianwei+'&k='+parent.parent.vod_piantou+'&n=跳过片头&my_title='+parent.parent.vod_name+'&my_url='+encodeURIComponent(window.location.href)+'" pluginspage="http://www.macromedia.com/go/getflashplayer" width="100%" height="'+Player.Height+'">';	
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