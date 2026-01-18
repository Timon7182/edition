package kz.danik.edition.view.dicamenities;

import com.vaadin.flow.router.Route;
import io.jmix.core.repository.JmixDataRepositoryContext;
import io.jmix.flowui.view.*;
import kz.danik.edition.entity.DicAmenities;
import kz.danik.edition.repository.DicAmenitiesRepository;
import kz.danik.edition.view.main.MainView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

@Route(value = "dic-amenitieses", layout = MainView.class)
@ViewController(id = "edt_DicAmenities.list")
@ViewDescriptor(path = "dic-amenities-list-view.xml")
@LookupComponent("dicAmenitiesesDataGrid")
@DialogMode(width = "64em")
public class DicAmenitiesListView extends StandardListView<DicAmenities> {

    @Autowired
    private DicAmenitiesRepository repository;

    @Install(to = "dicAmenitiesesDl", target = Target.DATA_LOADER, subject = "loadFromRepositoryDelegate")
    private List<DicAmenities> loadDelegate(Pageable pageable, JmixDataRepositoryContext context) {
        return repository.findAll(pageable, context).getContent();
    }

    @Install(to = "dicAmenitiesesDataGrid.removeAction", subject = "delegate")
    private void dicAmenitiesesDataGridRemoveDelegate(final Collection<DicAmenities> collection) {
        repository.deleteAll(collection);
    }

    @Install(to = "pagination", subject = "totalCountByRepositoryDelegate")
    private Long paginationTotalCountByRepositoryDelegate(final JmixDataRepositoryContext context) {
        return repository.count(context);
    }
}