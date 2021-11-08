package server;


import com.fasterxml.jackson.databind.ObjectMapper;
import lib.organization.Organization;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class FileWorker {

    private final File file;
    private final ObjectMapper objectMapper;

    public FileWorker(File file,
                      ObjectMapper objectMapper) {
        this.file = file;
        this.objectMapper = objectMapper;
    }

    private boolean checkAccess(File file) {
        return (file.canWrite() && file.canRead() && file.exists());
    }

    public ArrayList<Organization> parse() {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file));
            Organization[] organizations = objectMapper.readValue(inputStreamReader, Organization[].class);
            return new ArrayList<>(Arrays.asList(organizations));
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }

    public void serialize(ArrayList<Organization> organizations) throws IOException {
        Organization[] organizationsArr = organizations.toArray(new Organization[0]);
        System.out.println("save collection to " + file.getAbsolutePath());

        try (FileWriter fos = new FileWriter(file)) {
            objectMapper.writeValue(fos, organizationsArr);
        }
    }

    public void clear() {
        file.delete();
    }
}
