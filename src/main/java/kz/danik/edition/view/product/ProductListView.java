package kz.danik.edition.view.product;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import kz.danik.edition.entity.Product;
import kz.danik.edition.view.main.MainView;


@Route(value = "products", layout = MainView.class)
@ViewController(id = "edt_Product.list")
@ViewDescriptor(path = "product-list-view.xml")
@LookupComponent("productsDataGrid")
@DialogMode(width = "64em")
public class ProductListView extends StandardListView<Product> {
}