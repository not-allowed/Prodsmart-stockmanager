package controllers;

import models.Order;
import models.StockMovement;
import play.data.binding.Binder;
import play.db.Model;
import services.StockManager;

import java.lang.reflect.Constructor;

public class StockMovements extends CRUD {

    // Overriding this method to intercept save and for handle by StockManager
    public static void create() throws Exception {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        Constructor<?> constructor = type.entityClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        Model object = (Model) constructor.newInstance();
        Binder.bindBean(params.getRootParamNode(), "object", object);
        object._save();
        StockManager.handle((StockMovement) object);
        flash.success(play.i18n.Messages.get("crud.created", type.modelName));
        if (params.get("_save") != null) {
            redirect(request.controller + ".list");
        }
        if (params.get("_saveAndAddAnother") != null) {
            redirect(request.controller + ".blank");
        }
        redirect(request.controller + ".show", object._key());
    }
}
