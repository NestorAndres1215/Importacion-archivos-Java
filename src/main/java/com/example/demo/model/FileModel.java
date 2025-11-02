package com.example.demo.model;

import com.example.demo.util.MensajesValidacion;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "file_model")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    @NotBlank(message = MensajesValidacion.FILE_NAME_REQUERIDO)
    private String name;

    @Column(nullable = false, length = 150)
    @NotBlank(message = MensajesValidacion.FILE_TYPE_REQUERIDO)
    private String type;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @NotNull(message = MensajesValidacion.FILE_DATA_REQUERIDO)
    private byte[] data;


}