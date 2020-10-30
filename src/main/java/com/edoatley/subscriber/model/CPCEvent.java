package com.edoatley.subscriber.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cpc-events")
public class CPCEvent {
    @Id
    private String eventUUID;
    private String eventType;
    private String eventPayload;
}
