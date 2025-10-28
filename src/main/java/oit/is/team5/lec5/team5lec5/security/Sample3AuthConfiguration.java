package oit.is.team5.lec5.team5lec5.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class Sample3AuthConfiguration {

  /**
   * 認証処理に関する設定（誰がどのようなロールでログインできるか）
   *
   * @return
   */
  @Bean
  public InMemoryUserDetailsManager userDetailsService() {

    // ユーザ名，パスワード，ロールを指定してbuildする
    // このときパスワードはBCryptでハッシュ化されているため，{bcrypt}とつける
    // ハッシュ化せずに平文でパスワードを指定する場合は{noop}をつける
    // user1/p@ss,user2/p@ss,admin/p@ss

    UserDetails user1 = User.withUsername("user1")
        .password("{bcrypt}$2y$10$ngxCDmuVK1TaGchiYQfJ1OAKkd64IH6skGsNw1sLabrTICOHPxC0e").roles("USER").build();
    UserDetails user2 = User.withUsername("user2")
        .password("{bcrypt}$2y$10$ngxCDmuVK1TaGchiYQfJ1OAKkd64IH6skGsNw1sLabrTICOHPxC0e").roles("USER").build();
    UserDetails admin = User.withUsername("admin")
        .password("{bcrypt}$2y$10$ngxCDmuVK1TaGchiYQfJ1OAKkd64IH6skGsNw1sLabrTICOHPxC0e").roles("ADMIN").build();
    // customer1 p@ss
    UserDetails customer1 = User.withUsername("c1")
        .password("{bcrypt}$2y$05$EJh/I4TZqkbzg0JShEp4Puc.R/ZxMzRSGr.LaGgrqOYS9qPJg/aya")
        .roles("CUSTOMER")
        .build();
    // customer2 p@ss
    UserDetails customer2 = User.withUsername("customer2")
        .password("{bcrypt}$2y$05$Ns.gflP9AQuatvzjXcj15eSA0.rmGmKCBOt4VNdToejlflu81wjS6")
        .roles("CUSTOMER")
        .build();
    // seller p@ss
    UserDetails seller = User.withUsername("seller")
        .password("{bcrypt}$2y$05$u68sbQ7ITMw9mmgy4pJlz.TN38sUslZrI1a5Z/z6qJ9hXYMzpEj7.")
        .roles("SELLER")
        .build();

    // 生成したユーザをImMemoryUserDetailsManagerに渡す（いくつでも良い）
    return new InMemoryUserDetailsManager(user1, user2, admin, customer1, customer2, seller);
  }

}
