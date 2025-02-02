package com.employeeproject.Allocation.Model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class EmployeeProjectSerializer extends StdSerializer<EmployeeProject> {
    public EmployeeProjectSerializer() {
        super(EmployeeProject.class);
    }

    @Override
    public void serialize(EmployeeProject value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("id", String.valueOf(value.getId()));
        if (value.getAllocation() != null) {
            gen.writeNumberField("allocation", value.getAllocation());
        } else {
            gen.writeNullField("allocation");
        }
        gen.writeStringField("employeeID", value.getEmployee() != null ? value.getEmployee().getEmployeeID() : null);
        gen.writeStringField("projectID", value.getProject() != null ? value.getProject().getProjectID() : null);
        gen.writeEndObject();
    }
}
