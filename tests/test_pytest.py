import pytest


@pytest.mark.regression
def test_answer1():
    assert func(4) == 5


@pytest.mark.regression2
def test_answer2():
    assert func(3) == 5


def func(x):
    return x + 1


@pytest.mark.regression
class TestClass:
    def test_method(self):
        pass
