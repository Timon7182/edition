package kz.danik.edition.view.dicamenities;

import com.vaadin.flow.router.Route;
import io.jmix.core.FetchPlan;
import io.jmix.core.SaveContext;
import io.jmix.flowui.view.*;
import kz.danik.edition.entity.DicAmenities;
import kz.danik.edition.repository.DicAmenitiesRepository;
import kz.danik.edition.view.main.MainView;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Route(value = "dic-amenitieses/:id", layout = MainView.class)
@ViewController(id = "edt_DicAmenities.detail")
@ViewDescriptor(path = "dic-amenities-detail-view.xml")
@EditedEntityContainer("dicAmenitiesDc")
public class DicAmenitiesDetailView extends StandardDetailView<DicAmenities> {

    @Autowired
    private DicAmenitiesRepository repository;

    @Install(to = "dicAmenitiesDl", target = Target.DATA_LOADER, subject = "loadFromRepositoryDelegate")
    private Optional<DicAmenities> loadDelegate(UUID id, FetchPlan fetchPlan) {
        return repository.findById(id, fetchPlan);
    }

    @Install(target = Target.DATA_CONTEXT)
    private Set<Object> saveDelegate(SaveContext saveContext) {
        return Set.of(repository.save(getEditedEntity()));
    }
}