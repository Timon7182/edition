package kz.danik.edition.view.dicproductcategory;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import kz.danik.edition.entity.DicProductCategory;
import kz.danik.edition.view.main.MainView;


@Route(value = "dic-product-categories", layout = MainView.class)
@ViewController(id = "edt_DicProductCategory.list")
@ViewDescriptor(path = "dic-product-category-list-view.xml")
@LookupComponent("dicProductCategoriesDataGrid")
@DialogMode(width = "64em")
public class DicProductCategoryListView extends StandardListView<DicProductCategory> {
}