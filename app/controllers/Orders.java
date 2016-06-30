package controllers;

import models.Item;
import models.Order;
import models.StockMovement;
import models.User;
import play.data.binding.Binder;
import play.db.Model;
import play.exceptions.TemplateNotFoundException;
import services.StockManager;

import java.lang.reflect.Constructor;
import java.util.List;

import static play.mvc.Controller.redirect;

public class Orders extends CRUD {

    public static void create() throws Exception {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        Constructor<?> constructor = type.entityClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        Model object = (Model) constructor.newInstance();
        Binder.bindBean(params.getRootParamNode(), "object", object);
        /*validation.valid(object);
        if (validation.hasErrors()) {
            renderArgs.put("error", play.i18n.Messages.get("crud.hasErrors"));
            try {
                render(request.controller.replace(".", "/") + "/blank.html", type, object);
            } catch (TemplateNotFoundException e) {
                render("CRUD/blank.html", type, object);
            }
        }*/
        object._save();
        StockManager.handle((Order)object);
        flash.success(play.i18n.Messages.get("crud.created", type.modelName));
        if (params.get("_save") != null) {
            redirect(request.controller + ".list");
        }
        if (params.get("_saveAndAddAnother") != null) {
            redirect(request.controller + ".blank");
        }
        redirect(request.controller + ".show", object._key());
    }

    public static void add() {
        Item i = Item.findById(1L);
        StockMovement stock = StockMovement.getNextWithAvailableItems(i);
        System.out.println(stock);
        Order order = Order.getNextToBeFulfilledForItem(i);
        System.out.println(order);

      /*  Order o = new Order(users.get(0), i, 3);
        o.save();

        orderCreated(o);*/
        //render();
    }

    public static void orderCreated(Order order) {
        StockManager sm = new StockManager();
        int total = sm.handle(order);
        render();
    }
}
