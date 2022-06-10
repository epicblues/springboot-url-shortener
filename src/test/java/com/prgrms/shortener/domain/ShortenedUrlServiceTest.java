package com.prgrms.shortener.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ShortenedUrlServiceTest {

  private static final String ORIGINAL_URL = "http://naver.com";
  private static final String KEY = "succEss";
  @InjectMocks
  private ShortenedUrlService shortenedUrlService;
  @Mock
  private ShortenedUrlRepository repository;

  @Mock
  private ShortenedUrlFactory urlFactory;

  @Test
  @DisplayName("db에서 이미 url이 등록되어 있으면 해당 url의 key값을 반환한다.")
  void should_return_saved_key_when_url_is_already_stored() {

    // Given
    ShortenedUrl storedUrl = new ShortenedUrl();
    String storedKey = "yaHoo12";
    storedUrl.assignKey(storedKey);
    storedUrl.assignOriginalUrl(ORIGINAL_URL);

    // When
    when(repository.findByOriginalUrl(ORIGINAL_URL)).thenReturn(Optional.of(storedUrl));
    String key = shortenedUrlService.shorten(ORIGINAL_URL);

    // Then
    assertThat(key).isEqualTo(storedKey);

  }

  @Test
  @DisplayName("요청한 url이 저장소에 존재하지 않으면, 새로운 key 값을 생성해서 반환해야 한다.")
  void delegate_creation_to_factory() {

    // Given
    ShortenedUrl createdUrl = new ShortenedUrl(1);
    createdUrl.assignKey(KEY);
    createdUrl.assignOriginalUrl(ORIGINAL_URL);

    // When
    when(repository.findByOriginalUrl(ORIGINAL_URL)).thenReturn(Optional.empty());
    when(urlFactory.createShortenedUrl(ORIGINAL_URL)).thenReturn(createdUrl);
    String returnedKey = shortenedUrlService.shorten(ORIGINAL_URL);

    // Then
    verify(urlFactory, times(1)).createShortenedUrl(ORIGINAL_URL);
    assertThat(returnedKey).isEqualTo(KEY).matches("[\\w\\d]{7}");
  }

}
