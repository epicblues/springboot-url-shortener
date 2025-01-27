package com.prgrms.shortener.domain.implementation;

import static org.assertj.core.api.Assertions.assertThat;

import com.prgrms.shortener.domain.ShortenedUrl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class JpaShortenedUrlFactoryTest {

  @Autowired
  private JpaShortenedUrlFactory urlFactory;

  @Test
  @DisplayName("originalUrl을 사용하여 ShortenedUrl 엔티티를 만들 수 있어야 한다.")
  @Transactional
  void can_create_shortenedUrl_using_originalUrl() {
    // Given
    String originalUrl = "http://naver.com";

    // When
    ShortenedUrl createdUrl = urlFactory.createShortenedUrl(originalUrl);

    // Then
    assertThat(createdUrl.getOriginalUrl()).isEqualTo(originalUrl);
    assertThat(createdUrl.getId()).isNotNull();
    assertThat(createdUrl.getShortenedKey()).matches("[\\d\\w]{7}");

  }
}
