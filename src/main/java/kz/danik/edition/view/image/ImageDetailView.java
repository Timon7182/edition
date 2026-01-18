package kz.danik.edition.view.image;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import kz.danik.edition.entity.Image;
import kz.danik.edition.view.main.MainView;

@Route(value = "images/:id", layout = MainView.class)
@ViewController(id = "edt_Image.detail")
@ViewDescriptor(path = "image-detail-view.xml")
@EditedEntityContainer("imageDc")
public class ImageDetailView extends StandardDetailView<Image> {
}