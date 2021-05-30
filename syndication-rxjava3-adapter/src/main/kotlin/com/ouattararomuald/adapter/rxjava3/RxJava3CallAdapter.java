/*
 * Copyright (C) 2016 Jake Wharton
 * Copyright (C) 2019 Romuald Ouattara
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ouattararomuald.adapter.rxjava3;

import com.ouattararomuald.syndication.CallAdapter;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import okhttp3.Call;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class RxJava3CallAdapter<SyndicationType> extends CallAdapter<SyndicationType, Object> {

  private final @Nullable Scheduler scheduler;
  private final boolean isAsync;
  private final boolean isFlowable;
  private final boolean isSingle;
  private final boolean isMaybe;

  private final Parser<SyndicationType> parser;

  RxJava3CallAdapter(@Nullable Scheduler scheduler, boolean isAsync, boolean isFlowable,
      boolean isSingle, boolean isMaybe, Class<SyndicationType> clazz) {
    this.scheduler = scheduler;
    this.isAsync = isAsync;
    this.isFlowable = isFlowable;
    this.isSingle = isSingle;
    this.isMaybe = isMaybe;
    this.parser = xml -> parseXml(xml, clazz);
  }

  @Override public Object adapt(@NotNull Call call, @NotNull Class<SyndicationType> clazz) {
    Observable<?> observable = isAsync
        ? new CallEnqueueObservable<>(call, parser)
        : new CallExecuteObservable<>(call, parser);

    if (scheduler != null) {
      observable = observable.subscribeOn(scheduler);
    }

    if (isFlowable) {
      return observable.toFlowable(BackpressureStrategy.LATEST);
    }
    if (isSingle) {
      return observable.singleOrError();
    }
    if (isMaybe) {
      return observable.singleElement();
    }
    return RxJavaPlugins.onAssembly(observable);
  }
}
