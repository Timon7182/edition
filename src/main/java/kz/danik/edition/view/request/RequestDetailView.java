package kz.danik.edition.view.request;

import com.vaadin.flow.router.Route;
import io.jmix.core.FetchPlan;
import io.jmix.core.SaveContext;
import io.jmix.flowui.view.*;
import kz.danik.edition.entity.Request;
import kz.danik.edition.repository.RequestRepository;
import kz.danik.edition.view.main.MainView;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Route(value = "requests/:id", layout = MainView.class)
@ViewController(id = "edt_Request.detail")
@ViewDescriptor(path = "request-detail-view.xml")
@EditedEntityContainer("requestDc")
public class RequestDetailView extends StandardDetailView<Request> {

    @Autowired
    private RequestRepository repository;

    @Install(to = "requestDl", target = Target.DATA_LOADER, subject = "loadFromRepositoryDelegate")
    private Optional<Request> loadDelegate(UUID id, FetchPlan fetchPlan) {
        return repository.findById(id, fetchPlan);
    }

    @Install(target = Target.DATA_CONTEXT)
    private Set<Object> saveDelegate(SaveContext saveContext) {
        // Request has the following @Composition attributes: productRequest.
        // Make sure they have CascadeType.ALL in @OneToMany annotation.
        return Set.of(repository.save(getEditedEntity()));
    }
}