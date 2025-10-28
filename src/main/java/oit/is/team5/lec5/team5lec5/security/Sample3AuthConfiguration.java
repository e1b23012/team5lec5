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
   * 認可処理に関する設定（認証されたユーザがどこにアクセスできるか）
   *
   * @param http
   * @return
   * @throws Exception
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.formLogin(login -> login
        .permitAll())
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/")) // ログアウト後に / にリダイレクト
        .authorizeHttpRequests(authz -> authz
            .requestMatchers("/sample5/**").authenticated() // /sample5/以下は認証済みであること

            .anyRequest().permitAll()) // 上記以外は全員アクセス可能
        .csrf(csrf -> csrf
            .ignoringRequestMatchers("/h2-console/*", "/sample5*/**")) // sample2用にCSRF対策を無効化
        .headers(headers -> headers
            .frameOptions(frameOptions -> frameOptions
                .sameOrigin()));
    return http.build();
  }

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
