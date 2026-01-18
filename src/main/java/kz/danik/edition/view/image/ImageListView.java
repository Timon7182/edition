package kz.danik.edition.view.image;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import kz.danik.edition.entity.Image;
import kz.danik.edition.view.main.MainView;


@Route(value = "images", layout = MainView.class)
@ViewController(id = "edt_Image.list")
@ViewDescriptor(path = "image-list-view.xml")
@LookupComponent("imagesDataGrid")
@DialogMode(width = "64em")
public class ImageListView extends StandardListView<Image> {
}