package kz.danik.edition.view.productbooking;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import kz.danik.edition.entity.ProductBooking;
import kz.danik.edition.view.main.MainView;

@Route(value = "product-bookings/:id", layout = MainView.class)
@ViewController(id = "edt_ProductBooking.detail")
@ViewDescriptor(path = "product-booking-detail-view.xml")
@EditedEntityContainer("productBookingDc")
public class ProductBookingDetailView extends StandardDetailView<ProductBooking> {
}