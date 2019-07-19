# myspringboot

## Filter, JSP, Thymeleaf的 学习：
### 在项目中定义了两个Filter，分别为comm/MyFilter, comm/MyFilter2, 在WebConfiguration中通过order配置了这两个Filter的执行顺序(order的值设置的越低，越优先执行)
### 在项目中，开始配置了jsp，可以作为视图访问生效，当配置Thymeleaf后，就不能通过controller访问到jsp了

## Spring Security的 学习：
### 在登录后，如何拿到自己的基本信息，用户名和密码以及其他
#### 在controller的方法内通过SecurityContextHolder.getContext().getAuthentication()即可获取到用户信息
### 保持会话是利用session还是cookie进行的
#### 使用cookie保存JSESSIONID进行会话，将JSESSIONID=XXXXX，作为Cookie的值保存到一个请求的header中，同样可以获取数据，前提是xxxx是有效的；
#### 在测试过程中遇到一个问题，就是GET请求能够获取rest接口的数据，但是post的时候，就会报错403，这个是csrf，配置 Spring Security，那么 CSRF 保护默认是开启的，在 POST方式提交表单的时候就必须验证 Token，如果没有，那么自然也就是 403 没权限，在SecurityConfig中关闭csrf就可以了
#### 每次登陆后，JSESSIONID就发生了变化
#### 在登录成功后，需要在AuthenticationProvider中，将AuthenticationToken设置为true，不然后面的请求又会重复调用provider中的authenticate方法（但是JSESSION不会发生变化了）
#### 有一个疑惑，在登录后，cookie中的JSESSIONID=xxx1,但是调用接口，获取登录的信息中details.sessionId=xxx2,且xxx1与xxx2不同，details.sessionId存放的是啥玩意？
### 登陆时间的有效性，就跟tomcat设置有效期一样

## 下一步：
### 如果从数据库中获取用户信息和权限信息
### 如果进行单点登录
### jwt
### 现在使用的是UsernamePasswordAuthenticationToken，自定义AuthentionToken如何实现验证，当进行登录的时候，希望传递到Provider中的Authentication对象，是我们自定义的那个，不是默认的UsernamePasswordAuthenticationToken，这个应该需要下载源码进行解析
### 再次了解Chat >> Spring Security在互联网项目中的实战分享
### websocket


