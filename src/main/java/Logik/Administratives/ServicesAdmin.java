package Logik.Administratives;

import Logik.Services;

public abstract class ServicesAdmin extends Services {
    @Override
    protected abstract void datenAnlegen();

    @Override
    protected abstract void datenMutieren();


    @Override
    protected void datenLoeschen() {
    }

    @Override
    protected abstract String[] attributenArrayFuerTabelle();

}
