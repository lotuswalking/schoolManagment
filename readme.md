#这是一个用来练手的Java Spring Boot App.

##当前已经完成登录部分.连接Mysql数据库.在启动首页的时候进行用户数据初始化,已经可以初始化数据.但是登录看起来还不成

通过计划任务实现了数据初始化

数据库已经替换为内存型的H2数据库

完成了students显示页面

在首页增加了注销按钮

完成了多数据配置工作.运行起来不错,修改文件列表:

###1. 新建数据库配置文件.在configuration目录下

###2. 在application.yml为每个连接创建连接信息

###3. 将两个repository分开,如果所有repository在一个包里面,将无法进行切换

###4. 后续问题,如何热切换数据库?如果按照年份来创建不同数据库,应该如何方便的使用?要不要用ovieride?

### 5.使用thymeleaf security 5模块进行网页上面的用户名称已经权限组配置

###6. [参考引文](https://www.thymeleaf.org/doc/articles/springsecurity.html)

分页功能说明:需要传入第几页和煤业显示的条目数量.

`
int pageNo = (pathVarsMap.get("pageNo") == null)?1: Integer.parseInt(pathVarsMap.get("pageNo"));  //读入查询的页数
int pageSize = (pathVarsMap.get("pageSize") == null)?defaultPagesize: Integer.parseInt(pathVarsMap.get("pageSize"));  //读出每页显示item数目
Pageable pageable = PageRequest.of(pageNo-1,pageSize);   //定义页面查询的对象,第几页,每页显示数量
Page<Student> pages =  studentRepository.findAll(pageable);   //找出一页数据内容
List<Student> students = pages.getContent();    //找到需要显示再页面的对象内容
List<Integer> pageNums =  new ArrayList<Integer>();  
if(pageNo > 1) pageNums.add(pageNo-1);
pageNums.add(pageNo);   
if(pageNo < pages.getTotalPages()) pageNums.add(pageNo+1);  //如果存在的话,仅仅显示前一页,当前页与后一页  
model.addAttribute("students",students);  //为页面提供内容数据
model.addAttribute("totalPages",pages.getTotalPages());  //提供总页面数量
model.addAttribute("currentPage",pageNo);  //提供当前页面编号
model.addAttribute("totalItems",pages.getTotalElements());  //总的条目数量
model.addAttribute("pageSize",pageSize);    //每页显示数量
model.addAttribute("pageNums",pageNums);//显示页面编号清单
`
多数据库配置说明:
`
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.example.schoolmanagement.jpa.school",   //扫描repository和entity所在的包位置,好像关系不大
entityManagerFactoryRef = "**schoolEntityManagerFactory**",     //后文中定义的数据条目的工厂
transactionManagerRef = "**schoolTransactionManager**")     //后文中定义的事务管理器

@Bean(name="**schoolDataSourceProperties**")  
public DataSource schoolDataSource(@Qualifier("schoolDataSourceProperties") DataSourceProperties schoolDataSourceProperties)

@Bean(name = "**schoolDataSource**")
@ConfigurationProperties("school.datasource.configuration")
public DataSource schoolDataSource(@Qualifier("schoolDataSourceProperties") DataSourceProperties schoolDataSourceProperties)

private HashMap<String,Object> jpaProperties() {
HashMap<String, Object> properties = new HashMap<>();
properties.put("hibernate.hbm2ddl.auto", "update");
properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
return properties;
}

private String[] packeAges() {
return new String[]{"com.example.schoolmanagement.repo.system",
"com.example.schoolmanagement.jpa.school.entity"};
}

@Bean(name = "**schoolEntityManagerFactory**")
public LocalContainerEntityManagerFactoryBean schoolEntityManagerFactory(
@Qualifier("schoolDataSource") DataSource primaryDataSource,
EntityManagerFactoryBuilder primaryEntityManagerFactoryBuilder) {

        HashMap<String, Object> properties = new HashMap<>();
        return primaryEntityManagerFactoryBuilder
                .dataSource(primaryDataSource)
                .packages(**packeAges**())   //repository和entity所在的位置声明
                .persistenceUnit("schoolDataSource")
                .properties(jpaProperties())
                .build();
}

@Bean(name = "**schoolTransactionManager**")  
public PlatformTransactionManager schoolTransactionManager(
@Qualifier("schoolEntityManagerFactory") EntityManagerFactory primaryEntityManagerFactory) {

        return new JpaTransactionManager(primaryEntityManagerFactory);
}

`


**登录认证部分.**
`
@Component
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
@Autowired
protected UserService userService;

    @Override  //如果没有里面的确认,任何密码都成为可接受的密码
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        if(!userService.isValidCredentials(userDetails.getUsername(),usernamePasswordAuthenticationToken.getCredentials().toString())) {
            throw new BadCredentialsException("User authentication faild, check logs for more information");
        }
    }

    @Override
    protected UserDetails retrieveUser(String s, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        return userService.loadUserByUsername(s);
    }
}

`