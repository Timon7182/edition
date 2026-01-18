package kz.danik.edition.view.request;

import com.vaadin.flow.router.Route;
import io.jmix.core.repository.JmixDataRepositoryContext;
import io.jmix.flowui.view.*;
import kz.danik.edition.entity.Request;
import kz.danik.edition.repository.RequestRepository;
import kz.danik.edition.view.main.MainView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

@Route(value = "requests", layout = MainView.class)
@ViewController(id = "edt_Request.list")
@ViewDescriptor(path = "request-list-view.xml")
@LookupComponent("requestsDataGrid")
@DialogMode(width = "64em")
public class RequestListView extends StandardListView<Request> {

    @Autowired
    private RequestRepository repository;

    @Install(to = "requestsDl", target = Target.DATA_LOADER, subject = "loadFromRepositoryDelegate")
    private List<Request> loadDelegate(Pageable pageable, JmixDataRepositoryContext context) {
        return repository.findAll(pageable, context).getContent();
    }

    @Install(to = "requestsDataGrid.removeAction", subject = "delegate")
    private void requestsDataGridRemoveDelegate(final Collection<Request> collection) {
        repository.deleteAll(collection);
    }

    @Install(to = "pagination", subject = "totalCountByRepositoryDelegate")
    private Long paginationTotalCountByRepositoryDelegate(final JmixDataRepositoryContext context) {
        return repository.count(context);
    }
}