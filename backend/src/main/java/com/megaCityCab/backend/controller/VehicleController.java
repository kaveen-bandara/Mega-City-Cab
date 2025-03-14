package com.megaCityCab.backend.controller;

import com.megaCityCab.backend.dto.Response;
import com.megaCityCab.backend.service.connection.IVehicleService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    private IVehicleService vehicleService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> addNewVehicle(
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("licensePlate") String licensePlate,
            @RequestParam("vehicleType") String vehicleType,
            @RequestParam("model") String model,
            @RequestParam("color") String color,
            @RequestParam("description") String description,
            @RequestParam("fare") BigDecimal fare,
            @RequestParam("driverName") String driverName) {

        if (photo == null || photo.isEmpty()
                || licensePlate == null || licensePlate.isBlank()
                || vehicleType == null || vehicleType.isBlank()
                || model == null || model.isBlank()
                || color == null || color.isBlank()
                || description == null || description.isBlank()
                || fare == null || fare.compareTo(BigDecimal.ZERO) <= 0
                || driverName == null || driverName.isBlank()) {
            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("Please provide values for all fields!");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        Response response = vehicleService.addNewVehicle(photo, licensePlate, vehicleType, model, color, description, fare, driverName);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/types")
    public List<String> getAllVehicleTypes() {
        return vehicleService.getAllVehicleTypes();
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllVehicles() {
        Response response = vehicleService.getAllVehicles();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{vehicleId}")
    public ResponseEntity<Response> getVehicleById(@PathVariable("vehicleId") String vehicleId) {
        Response response = vehicleService.getVehicleById(vehicleId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{vehicleId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteVehicle(@PathVariable String vehicleId) {
        Response response = vehicleService.deleteVehicle(vehicleId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update/{vehicleId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateVehicleDetails(
            @PathVariable String vehicleId,
            @RequestParam(required = false) MultipartFile photo,
            @RequestParam(required = false) String licensePlate,
            @RequestParam(required = false) String vehicleType,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) BigDecimal fare,
            @RequestParam(required = false) String driverName) {
        Response response = vehicleService.updateVehicleDetails(vehicleId, licensePlate, vehicleType, model, color, description, fare, driverName, photo);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/all-available-vehicles")
    public ResponseEntity<Response> getAllAvailableVehicles() {
        Response response = vehicleService.getAllAvailableVehicles();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/available-vehicles-by-date-and-type")
    public ResponseEntity<Response> getAvailableVehiclesByDateAndVehicleType(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate endDate,
            @RequestParam(required = false) @NotBlank String vehicleType) {

        if (startDate == null || endDate == null || vehicleType.isBlank()) {
            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("All fields are required!");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        Response response = vehicleService.getAvailableVehiclesByDateAndVehicleType(startDate, endDate, vehicleType);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
