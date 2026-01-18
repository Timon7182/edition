package kz.danik.edition.view.productbooking;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import kz.danik.edition.entity.ProductBooking;
import kz.danik.edition.view.main.MainView;


@Route(value = "product-bookings", layout = MainView.class)
@ViewController(id = "edt_ProductBooking.list")
@ViewDescriptor(path = "product-booking-list-view.xml")
@LookupComponent("productBookingsDataGrid")
@DialogMode(width = "64em")
public class ProductBookingListView extends StandardListView<ProductBooking> {
}