# encrypt-aspect
基于切面与注解实现的自定义加解密

### 1.先使用方法注解声明 方法中需要对参数进行加密 解密 脱敏操作
> @DataDecryptSecurity: 该方法中有需要加密的字段
> @DataEncryptSecurity: 改方法中有需要解密的字段
### 2.再使用属性注解。
>属性注解分两类:<p>
>1. @EncryptField: 加密注解，该注解表示请求参数中有需要加密的字段，用于数据加密后存储，或者用于请求参数加密后在数据库中匹配加密数据，同时对返回的数据会进行加密。可以在该注解上指定正则表达式regex来过滤匹配的不需要加密的字段。<p>
>2. @DecryptField: 解密注解，表示对加密的字段进行解密，若字段没有加密，则直接返回明文。可以指定正则表达式regex来过滤匹配的不需要解密的字段<p>


> 注意：<p>
>1. 三个注解存在执行优先级关系，加密注解 > 解密注解,就是说如果同时使用三个注解在一个属性上，那么最终结果是脱敏的，如果只有加密和解密注解，那么最终结果是解密的，否则就是加密的。<p>
>2. 尽量在自己比较了解的类上使用，方便排查问题<p>