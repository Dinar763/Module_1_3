package org.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Writer {
    private Long id;
    private String firstName;
    private String lastName;
    private List<Post> posts;
    private Status status;
}
