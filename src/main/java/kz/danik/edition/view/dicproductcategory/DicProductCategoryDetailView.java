package kz.danik.edition.view.dicproductcategory;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import kz.danik.edition.entity.DicProductCategory;
import kz.danik.edition.view.main.MainView;

@Route(value = "dic-product-categories/:id", layout = MainView.class)
@ViewController(id = "edt_DicProductCategory.detail")
@ViewDescriptor(path = "dic-product-category-detail-view.xml")
@EditedEntityContainer("dicProductCategoryDc")
public class DicProductCategoryDetailView extends StandardDetailView<DicProductCategory> {
}