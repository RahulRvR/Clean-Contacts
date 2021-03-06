package me.panavtec.cleancontacts.domain.invoker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import me.panavtec.cleancontacts.domain.interactors.Interactor;
import me.panavtec.cleancontacts.presentation.invoker.InteractorInvoker;
import me.panavtec.presentation.common.outputs.InteractorOutput;

public class InteractorInvokerImp implements InteractorInvoker {

  private ExecutorService executor;

  public InteractorInvokerImp(ExecutorService executor) {
    this.executor = executor;
  }

  @Override public <T, E extends Exception> Future<T> execute(Interactor<T, E> interactor) {
    return executor.submit(interactor);
  }

  @Override public <T, E extends Exception> Future<T> execute(Interactor<T, E> interactor,
      InteractorOutput<T, E> output) {
    return execute(interactor, output, 0);
  }

  @Override public <T, E extends Exception> Future<T> execute(Interactor<T, E> interactor,
      InteractorOutput<T, E> output, int priority) {
    return (Future<T>) executor.submit(new InteractorOutputTask<>(interactor, priority, output));
  }
}
