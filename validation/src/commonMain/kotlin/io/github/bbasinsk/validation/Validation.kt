package io.github.bbasinsk.validation

/**
 * Collects multiple errors, composing many (Validation<A>, Validation<B>) => Validation<Z> for any domain object
 */
sealed interface Validation<out E, out A> {
    data class Valid<out A>(val value: A) : Validation<Nothing, A>
    data class Invalid<out E>(val errors: List<E>) : Validation<E, Nothing>

    companion object {
        /**
         * Create Valid from a value
         */
        fun <A> valid(value: A): Validation<Nothing, A> = Valid(value)

        /**
         * Create Invalid from errors
         */
        fun <E> invalid(error: E, vararg errors: E): Validation<E, Nothing> = Invalid(listOf(error) + errors.toList())

        /**
         * Catch exceptions and convert them to Invalid
         */
        inline fun <A> runCatching(f: () -> A): Validation<Throwable, A> =
            try {
                valid(f())
            } catch (e: Throwable) {
                invalid(e)
            }

        /**
         * Require a value to be not null, otherwise return an error
         */
        fun <E, A> requireNotNull(value: A?, error: () -> E): Validation<E, A> =
            if (value == null) invalid(error()) else valid(value)

        fun <E, A> fromResult(result: Result<A>, error: (Throwable) -> E): Validation<E, A> =
            result.fold({ valid(it) }, { invalid(error(it)) })

        /**
         * Creates a Validation from a sequence of Validations, collecting all errors
         */
        fun <E, A> sequence(validations: List<Validation<E, A>>): Validation<E, List<A>> =
            validations.fold(Valid(emptyList())) { acc, validation ->
                when (acc) {
                    is Invalid -> Invalid(validation.fold({ acc.errors }) { acc.errors + it })
                    is Valid -> when (validation) {
                        is Valid -> Valid(acc.value + validation.value)
                        is Invalid -> Invalid(validation.errors)
                    }
                }
            }

        /**
         * Composes many (Validation<A1>, Validation<A2>) => Validation<B> for any domain object
         */
        inline fun <E, A1, B> build(
            a1: Validation<E, A1>,
            t: (A1) -> B
        ): Validation<E, B> =
            a1.mapValid { t(it) }

        /**
         * Composes many (Validation<A1>, Validation<A2>) => Validation<B> for any domain object
         */
        inline fun <E, A1, A2, B> build(
            a1: Validation<E, A1>,
            a2: Validation<E, A2>,
            t: (A1, A2) -> B
        ): Validation<E, B> =
            (a1 zip a2).mapValid { t(it.first, it.second) }

        /**
         * Composes many (Validation<A1>, Validation<A2>) => Validation<B> for any domain object
         */
        inline fun <E, A1, A2, A3, B> build(
            a1: Validation<E, A1>,
            a2: Validation<E, A2>,
            a3: Validation<E, A3>,
            t: (A1, A2, A3) -> B
        ): Validation<E, B> =
            (a1 zip a2 zip a3).mapValid {
                t(
                    it.first.first,
                    it.first.second,
                    it.second
                )
            }

        /**
         * Composes many (Validation<A1>, Validation<A2>) => Validation<B> for any domain object
         */
        inline fun <E, A1, A2, A3, A4, B> build(
            a1: Validation<E, A1>,
            a2: Validation<E, A2>,
            a3: Validation<E, A3>,
            a4: Validation<E, A4>,
            t: (A1, A2, A3, A4) -> B
        ): Validation<E, B> =
            (a1 zip a2 zip a3 zip a4).mapValid {
                t(
                    it.first.first.first,
                    it.first.first.second,
                    it.first.second,
                    it.second
                )
            }

        /**
         * Composes many (Validation<A1>, Validation<A2>) => Validation<B> for any domain object
         */
        inline fun <E, A1, A2, A3, A4, A5, B> build(
            a1: Validation<E, A1>,
            a2: Validation<E, A2>,
            a3: Validation<E, A3>,
            a4: Validation<E, A4>,
            a5: Validation<E, A5>,
            t: (A1, A2, A3, A4, A5) -> B
        ): Validation<E, B> =
            (a1 zip a2 zip a3 zip a4 zip a5).mapValid {
                t(
                    it.first.first.first.first,
                    it.first.first.first.second,
                    it.first.first.second,
                    it.first.second,
                    it.second
                )
            }

        /**
         * Composes many (Validation<A1>, Validation<A2>) => Validation<B> for any domain object
         */
        inline fun <E, A1, A2, A3, A4, A5, A6, B> build(
            a1: Validation<E, A1>,
            a2: Validation<E, A2>,
            a3: Validation<E, A3>,
            a4: Validation<E, A4>,
            a5: Validation<E, A5>,
            a6: Validation<E, A6>,
            t: (A1, A2, A3, A4, A5, A6) -> B
        ): Validation<E, B> =
            (a1 zip a2 zip a3 zip a4 zip a5 zip a6).mapValid {
                t(
                    it.first.first.first.first.first,
                    it.first.first.first.first.second,
                    it.first.first.first.second,
                    it.first.first.second,
                    it.first.second,
                    it.second
                )
            }

        /**
         * Composes many (Validation<A1>, Validation<A2>) => Validation<B> for any domain object
         */
        inline fun <E, A1, A2, A3, A4, A5, A6, A7, B> build(
            a1: Validation<E, A1>,
            a2: Validation<E, A2>,
            a3: Validation<E, A3>,
            a4: Validation<E, A4>,
            a5: Validation<E, A5>,
            a6: Validation<E, A6>,
            a7: Validation<E, A7>,
            t: (A1, A2, A3, A4, A5, A6, A7) -> B
        ): Validation<E, B> =
            (a1 zip a2 zip a3 zip a4 zip a5 zip a6 zip a7).mapValid {
                t(
                    it.first.first.first.first.first.first,
                    it.first.first.first.first.first.second,
                    it.first.first.first.first.second,
                    it.first.first.first.second,
                    it.first.first.second,
                    it.first.second,
                    it.second
                )
            }

        /**
         * Composes many (Validation<A1>, Validation<A2>) => Validation<B> for any domain object
         */
        inline fun <E, A1, A2, A3, A4, A5, A6, A7, A8, B> build(
            a1: Validation<E, A1>,
            a2: Validation<E, A2>,
            a3: Validation<E, A3>,
            a4: Validation<E, A4>,
            a5: Validation<E, A5>,
            a6: Validation<E, A6>,
            a7: Validation<E, A7>,
            a8: Validation<E, A8>,
            t: (A1, A2, A3, A4, A5, A6, A7, A8) -> B
        ): Validation<E, B> =
            (a1 zip a2 zip a3 zip a4 zip a5 zip a6 zip a7 zip a8).mapValid {
                t(
                    it.first.first.first.first.first.first.first,
                    it.first.first.first.first.first.first.second,
                    it.first.first.first.first.first.second,
                    it.first.first.first.first.second,
                    it.first.first.first.second,
                    it.first.first.second,
                    it.first.second,
                    it.second
                )
            }

        /**
         * Composes many (Validation<A1>, Validation<A2>) => Validation<B> for any domain object
         */
        inline fun <E, A1, A2, A3, A4, A5, A6, A7, A8, A9, B> build(
            a1: Validation<E, A1>,
            a2: Validation<E, A2>,
            a3: Validation<E, A3>,
            a4: Validation<E, A4>,
            a5: Validation<E, A5>,
            a6: Validation<E, A6>,
            a7: Validation<E, A7>,
            a8: Validation<E, A8>,
            a9: Validation<E, A9>,
            t: (A1, A2, A3, A4, A5, A6, A7, A8, A9) -> B
        ): Validation<E, B> =
            (a1 zip a2 zip a3 zip a4 zip a5 zip a6 zip a7 zip a8 zip a9).mapValid {
                t(
                    it.first.first.first.first.first.first.first.first,
                    it.first.first.first.first.first.first.first.second,
                    it.first.first.first.first.first.first.second,
                    it.first.first.first.first.first.second,
                    it.first.first.first.first.second,
                    it.first.first.first.second,
                    it.first.first.second,
                    it.first.second,
                    it.second
                )
            }

        /**
         * Composes many (Validation<A1>, Validation<A2>) => Validation<B> for any domain object
         */
        inline fun <E, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B> build(
            a1: Validation<E, A1>,
            a2: Validation<E, A2>,
            a3: Validation<E, A3>,
            a4: Validation<E, A4>,
            a5: Validation<E, A5>,
            a6: Validation<E, A6>,
            a7: Validation<E, A7>,
            a8: Validation<E, A8>,
            a9: Validation<E, A9>,
            a10: Validation<E, A10>,
            t: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10) -> B
        ): Validation<E, B> =
            (a1 zip a2 zip a3 zip a4 zip a5 zip a6 zip a7 zip a8 zip a9 zip a10).mapValid {
                t(
                    it.first.first.first.first.first.first.first.first.first,
                    it.first.first.first.first.first.first.first.first.second,
                    it.first.first.first.first.first.first.first.second,
                    it.first.first.first.first.first.first.second,
                    it.first.first.first.first.first.second,
                    it.first.first.first.first.second,
                    it.first.first.first.second,
                    it.first.first.second,
                    it.first.second,
                    it.second
                )
            }

        /**
         * Composes many (Validation<A1>, Validation<A2>) => Validation<B> for any domain object
         */
        inline fun <E, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, B> build(
            a1: Validation<E, A1>,
            a2: Validation<E, A2>,
            a3: Validation<E, A3>,
            a4: Validation<E, A4>,
            a5: Validation<E, A5>,
            a6: Validation<E, A6>,
            a7: Validation<E, A7>,
            a8: Validation<E, A8>,
            a9: Validation<E, A9>,
            a10: Validation<E, A10>,
            a11: Validation<E, A11>,
            t: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11) -> B
        ): Validation<E, B> =
            (a1 zip a2 zip a3 zip a4 zip a5 zip a6 zip a7 zip a8 zip a9 zip a10 zip a11).mapValid {
                t(
                    it.first.first.first.first.first.first.first.first.first.first,
                    it.first.first.first.first.first.first.first.first.first.second,
                    it.first.first.first.first.first.first.first.first.second,
                    it.first.first.first.first.first.first.first.second,
                    it.first.first.first.first.first.first.second,
                    it.first.first.first.first.first.second,
                    it.first.first.first.first.second,
                    it.first.first.first.second,
                    it.first.first.second,
                    it.first.second,
                    it.second
                )
            }

        /**
         * Composes many (Validation<A1>, Validation<A2>) => Validation<B> for any domain object
         */
        inline fun <E, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, B> build(
            a1: Validation<E, A1>,
            a2: Validation<E, A2>,
            a3: Validation<E, A3>,
            a4: Validation<E, A4>,
            a5: Validation<E, A5>,
            a6: Validation<E, A6>,
            a7: Validation<E, A7>,
            a8: Validation<E, A8>,
            a9: Validation<E, A9>,
            a10: Validation<E, A10>,
            a11: Validation<E, A11>,
            a12: Validation<E, A12>,
            t: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12) -> B
        ): Validation<E, B> =
            (a1 zip a2 zip a3 zip a4 zip a5 zip a6 zip a7 zip a8 zip a9 zip a10 zip a11 zip a12).mapValid {
                t(
                    it.first.first.first.first.first.first.first.first.first.first.first,
                    it.first.first.first.first.first.first.first.first.first.first.second,
                    it.first.first.first.first.first.first.first.first.first.second,
                    it.first.first.first.first.first.first.first.first.second,
                    it.first.first.first.first.first.first.first.second,
                    it.first.first.first.first.first.first.second,
                    it.first.first.first.first.first.second,
                    it.first.first.first.first.second,
                    it.first.first.first.second,
                    it.first.first.second,
                    it.first.second,
                    it.second
                )
            }

        /**
         * Composes many (Validation<A1>, Validation<A2>) => Validation<B> for any domain object
         */
        inline fun <E, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, B> build(
            a1: Validation<E, A1>,
            a2: Validation<E, A2>,
            a3: Validation<E, A3>,
            a4: Validation<E, A4>,
            a5: Validation<E, A5>,
            a6: Validation<E, A6>,
            a7: Validation<E, A7>,
            a8: Validation<E, A8>,
            a9: Validation<E, A9>,
            a10: Validation<E, A10>,
            a11: Validation<E, A11>,
            a12: Validation<E, A12>,
            a13: Validation<E, A13>,
            t: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13) -> B
        ): Validation<E, B> =
            (a1 zip a2 zip a3 zip a4 zip a5 zip a6 zip a7 zip a8 zip a9 zip a10 zip a11 zip a12 zip a13).mapValid {
                t(
                    it.first.first.first.first.first.first.first.first.first.first.first.first,
                    it.first.first.first.first.first.first.first.first.first.first.first.second,
                    it.first.first.first.first.first.first.first.first.first.first.second,
                    it.first.first.first.first.first.first.first.first.first.second,
                    it.first.first.first.first.first.first.first.first.second,
                    it.first.first.first.first.first.first.first.second,
                    it.first.first.first.first.first.first.second,
                    it.first.first.first.first.first.second,
                    it.first.first.first.first.second,
                    it.first.first.first.second,
                    it.first.first.second,
                    it.first.second,
                    it.second
                )
            }

        /**
         * Composes many (Validation<A1>, Validation<A2>) => Validation<B> for any domain object
         */
        inline fun <E, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, B> build(
            a1: Validation<E, A1>,
            a2: Validation<E, A2>,
            a3: Validation<E, A3>,
            a4: Validation<E, A4>,
            a5: Validation<E, A5>,
            a6: Validation<E, A6>,
            a7: Validation<E, A7>,
            a8: Validation<E, A8>,
            a9: Validation<E, A9>,
            a10: Validation<E, A10>,
            a11: Validation<E, A11>,
            a12: Validation<E, A12>,
            a13: Validation<E, A13>,
            a14: Validation<E, A14>,
            t: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14) -> B
        ): Validation<E, B> =
            (a1 zip a2 zip a3 zip a4 zip a5 zip a6 zip a7 zip a8 zip a9 zip a10 zip a11 zip a12 zip a13 zip a14).mapValid {
                t(
                    it.first.first.first.first.first.first.first.first.first.first.first.first.first,
                    it.first.first.first.first.first.first.first.first.first.first.first.first.second,
                    it.first.first.first.first.first.first.first.first.first.first.first.second,
                    it.first.first.first.first.first.first.first.first.first.first.second,
                    it.first.first.first.first.first.first.first.first.first.second,
                    it.first.first.first.first.first.first.first.first.second,
                    it.first.first.first.first.first.first.first.second,
                    it.first.first.first.first.first.first.second,
                    it.first.first.first.first.first.second,
                    it.first.first.first.first.second,
                    it.first.first.first.second,
                    it.first.first.second,
                    it.first.second,
                    it.second
                )
            }
    }
}

/**
 * if Valid, apply the function to the value
 */
inline fun <E, A, B> Validation<E, A>.mapValid(f: (A) -> B): Validation<E, B> =
    when (this) {
        is Validation.Invalid -> this
        is Validation.Valid -> Validation.Valid(f(this.value))
    }

/**
 * if Invalid, apply the function to each of the errors
 */
inline fun <E, A, F> Validation<E, A>.mapInvalid(f: (E) -> F): Validation<F, A> =
    when (this) {
        is Validation.Valid -> this
        is Validation.Invalid -> Validation.Invalid(this.errors.map(f))
    }

/**
 * apply the onValid function to the value if Valid,
 * otherwise apply the onInvalid function to each of the errors
 */
inline fun <E, A, B> Validation<E, A>.fold(onValid: (A) -> B, onInvalid: (List<E>) -> B): B =
    when (this) {
        is Validation.Valid -> onValid(value)
        is Validation.Invalid -> onInvalid(errors)
    }

/**
 * Combines many (Validation<A1>, Validation<A2>) => Validation<Pair<A1, A2>> for any domain object
 *
 * Note: This is an extension function because lower bounds don't seem to exist in kotlin:
 * https://discuss.kotlinlang.org/t/generic-constraints-lower-bound/15074
 */
infix fun <E, A, B> Validation<E, A>.zip(other: Validation<E, B>): Validation<E, Pair<A, B>> =
    when (this) {
        is Validation.Invalid -> when (other) {
            is Validation.Invalid -> Validation.Invalid(this.errors.plus(other.errors))
            is Validation.Valid -> Validation.Invalid(this.errors)
        }

        is Validation.Valid -> when (other) {
            is Validation.Invalid -> Validation.Invalid(other.errors)
            is Validation.Valid -> Validation.Valid(this.value to other.value)
        }
    }

/**
 * Sequences one validation after the success of another.
 *
 * If the first validation is invalid, the second validation is not run. Does **not** accumulate errors.
 */
inline fun <E, A, B> Validation<E, A>.andThen(f: (A) -> Validation<E, B>): Validation<E, B> =
    when (this) {
        is Validation.Invalid -> this
        is Validation.Valid -> f(this.value)
    }

/**
 * Sequences one validation after the failure of another.
 *
 * If the first validation is valid, the second validation is not run. Does **not** accumulate errors.
 */
inline fun <E, A> Validation<E, A>.orElse(other: (List<E>) -> Validation<E, A>): Validation<E, A> =
    when (this) {
        is Validation.Valid -> this
        is Validation.Invalid -> other(this.errors)
    }

inline fun <E, A> Validation<E, A>.getOrElse(handle: (List<E>) -> A): A =
    when (this) {
        is Validation.Valid -> this.value
        is Validation.Invalid -> handle(this.errors)
    }

fun <E, A> Validation<E, A>.getOrNull(): A? =
    when (this) {
        is Validation.Valid -> this.value
        is Validation.Invalid -> null
    }

inline fun <E, A> Validation<E, A>.filter(error: () -> E, predicate: (A) -> Boolean): Validation<E, A> =
    andThen { if (predicate(it)) this else Validation.invalid(error()) }

inline fun <E, A, B> Validation<E, A>.onValid(f: (A) -> B): Validation<E, A> =
    when (this) {
        is Validation.Invalid -> this
        is Validation.Valid -> this.also { f(this.value) }
    }

inline fun <E, A, F> Validation<E, A>.onInvalid(f: (List<E>) -> F): Validation<E, A> =
    when (this) {
        is Validation.Invalid -> this.also { f(this.errors) }
        is Validation.Valid -> this
    }
