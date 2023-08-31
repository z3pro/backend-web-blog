package com.spring.api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import com.spring.payload.response.MessageResponse;
import com.spring.payload.response.UploadFileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.services.drive.model.File;
import com.spring.service.impl.FileManagerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/google-drive")
public class UploadFileControllor {
    @Autowired
    private final FileManagerService fileManagerService;

    @GetMapping({ "/" })
    public ResponseEntity<List<File>> listEverything() throws IOException, GeneralSecurityException {
        List<File> files = fileManagerService.listEverything();
        return ResponseEntity.ok().body(files);
    }

    @GetMapping({ "/list", "/list/{parentId}" })
    public ResponseEntity<List<File>> list(@PathVariable(required = false) String parentId)
            throws IOException, GeneralSecurityException {
        List<File> files = fileManagerService.listFolderContent(parentId);
        return ResponseEntity.ok(files);
    }

    @GetMapping("/download/{id}")
    public void download(@PathVariable String id, HttpServletResponse response)
            throws IOException, GeneralSecurityException {
        fileManagerService.downloadFile(id, response.getOutputStream());
    }

    @GetMapping("/directory/create")
    public ResponseEntity<String> createDirectory(@RequestParam String path) throws Exception {
        String parentId = fileManagerService.getFolderId(path);
        return ResponseEntity.ok("parentId: " + parentId);
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<?> uploadSingleFile(@RequestParam("file") MultipartFile[] files,
            @RequestParam(required = false) String path) {
        int filesize = files.length;
        AtomicReference<String> fileId = new AtomicReference<>("");
        AtomicReference<String> fileName = new AtomicReference<>("");
        Arrays.asList(files).forEach(
                file -> {
                    fileId.set(fileManagerService.uploadFile(file, "blog"));
                    fileName.set(file.getOriginalFilename());
                });

        if (filesize > 1) {
            return ResponseEntity.ok().body("files uploaded successfully");
        }
        return ResponseEntity.ok().body(new UploadFileResponse("https://drive.google.com/uc?id=" + fileId));
    }

    @GetMapping("/delete/{id}")
    public void delete(@PathVariable String id) throws Exception {
        fileManagerService.deleteFile(id);
    }

    @RequestMapping(value = "/preview/cv", method = RequestMethod.GET)
    protected String preivewSection(
            HttpServletRequest request,
            HttpSession httpSession,
            HttpServletResponse response) {
        try {
            byte[] documentInBytes = Files.readAllBytes(Path.of("root"));
            response.setDateHeader("Expires", -1);
            response.setContentType("application/pdf");
            response.setContentLength(documentInBytes.length);
            response.getOutputStream().write(documentInBytes);
        } catch (Exception ignored) {
        }
        return null;
    }
}
