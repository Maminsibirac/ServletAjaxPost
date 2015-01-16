<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<style>
   <%@include file='/resources/css/my_styles.css' %>
</style>


<html>
	<head>
    	<meta charset="utf-8">
        <title>My test page!</title>
</head>
<body>
 		<header class="header">
            <img src="images/logo/snow_logo.png" alt="" class="snow-logo">
     		<div class="text">
      			<p class="text-line-first">Твой экстрим,</p>
      			<p class="text-line-second">твои правила,</p>
      			<p class="text-line-third">твой взгляд!</p>
       		</div>

            <div class="social-logo">
            	<img src="images/logo/facebook_logo.png" alt="">
            	<img src="images/logo/twitter_logo.png">
            	<img src="images/logo/google_logo.png">
            	<img src="images/logo/red_blue_logo.png">
            </div>
            <div class="toolbar">
            	<p class="tool-first">Кто мы?</p>
                <div class="tool-second">Что мы умеем?</div>
                <div class="tool-third">Обратная связь</div>
            </div>
 		</header>

        <main>
        <div class="background">
        	<div class="video-content">
               <div class="content-first">
              	   <img style="border:6px solid white;" src="images/background/snowboard.png" alt="" width="470">
                   <img src="images/logo/arrow_left.png" class="arrow-left">
               	   <img src="images/logo/arrow_right.png" class="arrow-right">
                   <img src="images/logo/radio_button.png" class="radio-button-first" width="18">
                   <img src="images/logo/radio_button.png" class="radio-button-second" width="18">
                   <img src="images/logo/radio_button.png" class="radio-button-third" width="18">
               </div>

               <div class="content-second">
               	 <img style="border:2px solid white;" src="images/background/snow_blog_first.png" width = "200px" height="66" hspace="15">
                 <img src="images/background/video_text.png" class="arena">
                 <div class="text-video">Видео</div>
                 <img src="images/pictures/play.png" class="play">

                 <img style="border:2px solid white;" src="images/background/snow_blog_second.png" width="200" height="66" hspace="15" vspace="16">
                 <img src="images/background/video_text.png" class="photo">
                 <div class="text-photo">Фото</div>
                 <img style="border:2px solid white;" src="images/background/snow_blog_third.png" width ="200" height="66" hspace="15">
              	 <img src="images/background/video_text.png" class="snow">
                 <div class="text-press">Пресса <div class="press">о нас</div></div>
               </div>

            </div>

            <div class="article-content">
            	Ну и что ты сидишь?! Оторвись от дивана и вставай на сноуборд!
                <strong>Пришло твое время показать себя!</strong> А наши операторы и фотографы тебе в этом помогут :)
            </div>
            <div class="title">
            	Обратная связь
            </div>
            <article>
            	<div class="text">
            		Чтобы записать незабываемое видео или снять чумовую фотосессию в снежных горах на сноуборде, заполните форму и мы свяжемся с вами в ближайшее время.
            	</div>

            </article>

            <div class="ground">
             <div class="edit-form">
  				 <form action="sniffer" method="post">
					<div id="messenger"></div>
						<div class="form-text">
							<div class="name">Имя<span>*</span></div>
			  				<p class="indent" class="surname">Фамилия<span>*</span></p>
                        	<p class="indent"><div class="email">E-mail<span>*</span></div></p>
                            <p><div class="comments">Комментарий</div></p>
                        </div>
			 <div form-action>
			  <input id="first" name="first" type="text" value=""  class="text" autocomplete="off"/>
			  <p><input id="last" name="last" type="text" value=""  class="text" autocomplete="off"/></p>
			  <p><input id="email" name="email" type="text" value=""  class="text" autocomplete="off"/></p>
                        </div>
                        <div class="form-action-1">
                            <div><input id="photo" type="radio" name="rad" value="Фотосъемка" class="radio" checked="checked"/> Фотосъемка
                            <p class="first_check"><input id="video" type="radio" name="rad" value="Видеосъемка" class="radio"/> Видеосъемка</p></div>
			  				<div class="second_check"><input id="our" type="checkbox" name="our" value="1" checked="checked" class="radio"/> Наличие собственной экипировки</div>
			  				<div class="textarea"><textarea rows="5" id="comments" name="comments" class="box" autocomplete="off"></textarea></div>

						</div>
	     		   <div class="send">
					 <input type="submit" class="submit" value="Оставить заявку">
				   </div>
			    </form>
   			</div>
  		</div>

        </main>

        <footer>
        	<img src="images/pictures/snowboard_footer.png" class="snowboard-footer">
            <div class="text-contact">
            	644000, Город, ул. Улицаулица, 334
				<p>Телефон: <span class="contact-phone">(123) 34-56-78</span></p>
            </div>
            <div class="text-company">
            	© 2013 SnowRIP Company
            </div>
        </footer>
	</body>
</html>