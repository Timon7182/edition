package kz.danik.edition.view.product;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import kz.danik.edition.entity.Product;
import kz.danik.edition.view.main.MainView;

@Route(value = "products/:id", layout = MainView.class)
@ViewController(id = "edt_Product.detail")
@ViewDescriptor(path = "product-detail-view.xml")
@EditedEntityContainer("productDc")
public class ProductDetailView extends StandardDetailView<Product> {
}