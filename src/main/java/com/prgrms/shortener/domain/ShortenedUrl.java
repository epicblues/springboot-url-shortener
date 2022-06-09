package com.prgrms.shortener.domain;

import static com.google.common.base.Preconditions.checkArgument;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "shortened-url")
public class ShortenedUrl {

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
  @Column
  private String originalUrl;
  @Column(length = 8)
  private String key;

  public ShortenedUrl(Long id) {
    this.id = id;
  }

  public void assignOriginalUrl(String url) {
    this.originalUrl = url;
  }

  // Controller에서 출력 데이터를 확실하게 정한다.
  // 서버의 domain을 알고 있는 영역은 presentation layer
  public void assignKey(String key) {
    checkArgument(key.length() == 8, "key는 8개의 문자로 구성되어야 합니다.");
    this.key = key;
  }

  // 알고리즘을 enum 형태로 보관환 뒤에 entity에서 호출할 수 있도록 할까?
}
