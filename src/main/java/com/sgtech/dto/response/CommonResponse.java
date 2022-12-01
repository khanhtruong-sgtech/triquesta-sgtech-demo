package com.sgtech.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommonResponse {

    private Integer numberOfRecordsInserted;

    private String content;

}
