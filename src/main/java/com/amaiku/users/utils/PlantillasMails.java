package com.amaiku.users.utils;

public class PlantillasMails {

    public PlantillasMails() {
    }

    public String crearPlantillaRecuperacionPass(String nuevaPass){
        String html  ="<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<style>\n"
                + "table {\n"
                + "  border-collapse: collapse;\n"
                + "  width: 100%;\n"
                + "}\n"
                + "\n"
                + "th, td {\n"
                + "  text-align: left;\n"
                + "  padding: 8px;\n"
                + "}\n"
                + "\n"
                + "tr:nth-child(even){background-color: #f2f2f2}\n"
                + "\n"
                + "th {\n"
                + "  background-color: #31572C;\n"
                + "  color: white;\n"
                + "}\n"
                + "</style>\n"
                + "</head>\n"
                + "<body>\n"
                + "\n"
                + "<h2>Estimado/a</h2>\n"
                + "\n"
                + "<h4>Le confirmamos que has recuperado tu contraseña</h4>\n"
                + "\n"
                + "<h4>Su nueva contraseña es : "+ nuevaPass +"</h4>\n"
                + "\n"
                + "\n"
                + "<h4>Recomendamos que una vez que ingreso a la aplicacion, cambie su contraseña desde la sección Perfil</h4>\n"
                + "\n"
                + "<h4>Muchas gracias!!</h4>\n"
                + "\n"
                + "\n"
                + "</body>\n"
                + "</html>";

        return html;
    }

}
