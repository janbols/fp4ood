package be.guterfluss.immutability;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class Appartment {

    private final Address address;
    private final List<Resident> residents;

    public Appartment(Address address, List<Resident> residents) {
        this.address = address;
        this.residents = residents != null ?
                ImmutableList.copyOf(residents) :
                ImmutableList.<Resident>of();
    }

    public Address getAddress() { return address; }
    public List<Resident> getResidents() { return residents; }

    Appartment addResident(Resident resident) {
        List<Resident> newResidents = ImmutableList
                .<Resident>builder()
                .add(resident)
                .addAll(residents)
                .build();
        return new Appartment(this.address, newResidents);
    }
}
