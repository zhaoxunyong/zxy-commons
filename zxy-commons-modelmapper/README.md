# zxy-commons-modelmapper
Model与dto自动映射模块。
### 如果其他模块需要使用这些功能时，只需要在maven中加入：
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-modelmapper</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```
	
### spring框架使用说明
#### spring配置：
```xml
	<import resource="classpath:commons-modelmapper.xml" />
```

#### 使用：
- dto转model：
```java
	@Component
	public class Dto2UserMapping extends PropertyMapConfigurerSupport<UserDTO, User> {
	
	    @Override
	    public PropertyMap<UserDTO, User> mapping() {
	
	        return new PropertyMap<UserDTO, User>() {
	            @Override
	            protected void configure() {
	                map().setUserName(source.getName());
	                map().setPasswd(source.getPwd());
	            }
	        };
	    }
	}
```

- model转dto：
```java
	@Component
	public class User2DtoMapping extends PropertyMapConfigurerSupport<User, UserDTO> {
	
	    @Override
	    public PropertyMap<User, UserDTO> mapping() {
	
	        return new PropertyMap<User, UserDTO>() {
	            @Override
	            protected void configure() {
	                map().setName(source.getUserName());
	                map().setPwd(source.getPasswd());
	            }
	        };
	    }
	}
```

- 调用：
```java
	public class UserServiceFacadeImpl implements UserServiceFacade {
	
	    @Autowired
	    private UserService userService;
	    
	    @Autowired
	    private ModelMapper modelMapper;
	
	    /**
	     * {@inheritDoc}
	     */
	    public UserDTO getUserById(int userId) {
	        User user = userService.getUserById(userId);
	        return modelMapper.map(user, UserDTO.class);
	    }
	}
```

### 非spring框架使用说明：
- 编写helper类：
```java
	public final class UserMappingHelper {
	    
	    private UserMappingHelper() {}
	    private final static ModelMapper MAPPER = ModelMapperFactory.getModelMapper(new PropertyMap<UserDTO, User>() {
	        @Override
	        protected void configure() {
	            map().setUserName(source.getName());
	            map().setPasswd(source.getPwd());
	        }
	    }, new PropertyMap<User, UserDTO>() {
	        @Override
	        protected void configure() {
	            map().setName(source.getUserName());
	            map().setPwd(source.getPasswd());
	        }
	    });
	
	    /**
	     * User转换为UserDTO
	     * 
	     * @param user user
	     * @return UserDTO
	    */
	    public static UserDTO user2dto(User user) {
	        return MAPPER.map(user, UserDTO.class);
	    }
	
	    /**
	     * UserDTO转换为User
	     * 
	     * @param userDTO userDTO
	     * @return User
	    */
	    public static User dto2user(UserDTO userDTO) {
	        return MAPPER.map(userDTO, User.class);
	    }
	
	    /**
	     * User集合转换为UserDTO集合
	     * 
	     * @param users User集合
	     * @return UserDTO集合
	    */
	    public static List<UserDTO> users2dtos(List<User> users) {
	        return FluentIterable.from(users).transform(new Function<User, UserDTO>() {
	
	            @Override
	            public UserDTO apply(User user) {
	                return user2dto(user);
	            }
	        }).toList();
	    }
	
	
	    /**
	     * UserDTO集合转换为UserDTO集合
	     * 
	     * @param userDTOs UserDTO集合
	     * @return User集合
	    */
	    public static List<User> dtos2users(List<UserDTO> userDTOs) {
	        return FluentIterable.from(userDTOs).transform(new Function<UserDTO, User>() {
	
	            @Override
	            public User apply(UserDTO userDTO) {
	                return dto2user(userDTO);
	            }
	        }).toList();
	    }
	}
```

- 调用：
```java
	User user = UserMappingHelper.dto2user(dto);
```

