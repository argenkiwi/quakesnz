package nz.co.codebros.quakesnz

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

import javax.inject.Scope

/**
 * Created by Leandro on 23/11/2017.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
annotation class FragmentScope
